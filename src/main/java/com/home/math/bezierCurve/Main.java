package com.home.math.bezierCurve;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main extends Application {

    public static final int N = 600;

    public static final double WHOLE = 1000;




    @Override
    public void start(Stage stage) throws Exception {
        final double drawSpeed = 0.01;
        final int bezierPointsAmount = 4;

        Pane root = new Pane();
        root.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-color: #ff9c16;");

        Scene scene = new Scene(root, WHOLE, WHOLE);
        stage.setScene(scene);

        Timeline timeline = new Timeline();
        Duration timepoint = Duration.ZERO ;
        Duration pause = Duration.seconds(drawSpeed);

        final BiFunction<Double, Double, Double> xShift = BezierUtils.up();
        final BiFunction<Double, Double, Double> yShift = BezierUtils.up();

        final double shiftLength = WHOLE / 6;
        List<BezierPoint> startPoints = InitialPointsSets.random(bezierPointsAmount);
        BezierUtils.move(startPoints, BezierPoint::getX, BezierPoint::setX, shiftLength, xShift);
        BezierUtils.move(startPoints, BezierPoint::getY, BezierPoint::setY, shiftLength, yShift);

        AtomicReference<List<List<? extends Node>>> prevLinesState = new AtomicReference<>();

        for (int i = 0; i < N + 1; i++) {
            timepoint = timepoint.add(pause);
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(
                    timepoint,
                    e -> {
                        final List<List<? extends Node>> nodesLists = prevLinesState.get();
                        if (nodesLists != null) {
                            this.retainMainPoint(nodesLists);
                            nodesLists.forEach(lines -> root.getChildren().removeAll(lines));

                            final List<List<? extends Node>> nextLineState = this.drawAtTimeMoment(startPoints, finalI);
                            nextLineState.forEach(lines -> root.getChildren().addAll(lines));
                            prevLinesState.set(nextLineState);
                        } else {
                            prevLinesState.set(this.drawAtTimeMoment(startPoints, finalI));
                        }
                    });
            timeline.getKeyFrames().add(keyFrame);
        }

        String titleTamplate = "Bezier %s-Curve";
        stage.setTitle(String.format(titleTamplate, startPoints.size()));

        stage.show();
        timeline.play();

    }

    private void retainMainPoint(List<List<? extends Node>> nodesLists) {
        final Line mainPoint = BezierUtils.getMainPoint(nodesLists);
        AtomicReference<List<? extends Node>> mainPointList = new AtomicReference<>();
        nodesLists.forEach(lines -> {
                    if (lines.contains(mainPoint)) {
                        mainPointList.set(lines);
                    }
                }
        );
        nodesLists.remove(mainPointList.get());
    }

    private static Color getColor() {
        final int max = 255;
        final int endInclusive = 250;
        double blue = RandomUtils.nextDouble(0, endInclusive) / max;
        double green = RandomUtils.nextDouble(0, endInclusive) / max;
        double red = RandomUtils.nextDouble(0, endInclusive) / max;
        final Color color = Color.color(red, green, blue);
        return color;
    }

    private List<List<? extends Node>> drawAtTimeMoment(List<BezierPoint> startPoints, int timeMoment) {
        final List<List<BezierPoint>> pointsLists = BezierUtils.getPoints(startPoints, timeMoment * WHOLE / N, WHOLE);

        final List<List<Line>> linesList = BezierUtils.getLinesLists(pointsLists);
        final Line mainPoint = BezierUtils.getMainPointAsLine(pointsLists);

        StyleUtils.setGradientColorStroke(linesList, 0.2);
        StyleUtils.setColorAndStroke(mainPoint, Color.RED, 4.0);

        final List<List<? extends Node>> nodes = new ArrayList<>();

        final List<Line> startLines = BezierUtils.getLines(startPoints);

        nodes.add(Collections.singletonList(mainPoint));
        final List<Line> startPointsAsLines = BezierUtils.getPointsAsLines(startPoints);
        StyleUtils.setColorAndStroke(Collections.singletonList(startPointsAsLines), Color.BLACK, 5);

        nodes.add(startPointsAsLines);
        nodes.add(startLines);
        nodes.addAll(linesList);

        return nodes;
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
