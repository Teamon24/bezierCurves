package com.home.math.bezierCurve;

import com.google.common.collect.Lists;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BezierUtils {

    static List<List<Line>> getLinesLists(List<List<BezierPoint>> points) {
        List<List<Line>> nodes = new ArrayList<>();

        for (List<BezierPoint> bezierPoints : points) {
            List<Line> nodesList = getLines(bezierPoints);
            nodes.add(nodesList);
        }
        return nodes;
    }

    static List<Line> getLines(List<BezierPoint> points) {
        List<Line> nodesList = new ArrayList<>();
        if (points.size() == 1) {
            BezierPoint point = points.get(0);
            nodesList.add(getPointAsLine(point));
        } else {
            for (int j = 0; j < points.size() - 1; j++) {
                BezierPoint bezierPoint1 = points.get(j);
                BezierPoint bezierPoint2 = points.get(j + 1);
                nodesList.add(getLine(bezierPoint1, bezierPoint2));
            }
        }
        return nodesList;
    }

    public static List<List<Line>> getPoints(List<List<BezierPoint>> points) {
        List<List<Line>> nodes = new ArrayList<>();

        for (List<BezierPoint> bezierPoints : points) {
            List<Line> nodesList = new ArrayList<>();
            if (bezierPoints.size() == 1) {
                nodesList.add(getPointAsLine(bezierPoints.get(0)));
            } else {
                for (int j = 0; j < bezierPoints.size() - 1; j++) {
                    nodesList.add(getPointAsLine(bezierPoints.get(j)));
                    nodesList.add(getPointAsLine(bezierPoints.get(j + 1)));
                }
            }
            nodes.add(nodesList);
        }
        return nodes;
    }

    /**
     * Получение главной точки, которая и является частью кривой Безье.
     * @param points набор точек.
     * @return
     */
    public static Line getMainPointAsLine(List<List<BezierPoint>> points) {
        Line node = new Line();

        for (List<BezierPoint> bezierPoints : points) {
            if (bezierPoints.size() == 1) {
                node = getPointAsLine(bezierPoints.get(0));
            }
        }
        return node;
    }

    public static Line getMainPoint(List<List<? extends Node>> nodesLists) {
        for (List<? extends Node> nodeList : nodesLists) {
            if (nodeList.size() == 1) {
                final Node node = Lists.newLinkedList(nodeList).getFirst();
                if (node instanceof Line) {
                    final Line line = (Line) node;
                    if (isPoint(line)) {
                        return line;
                    }
                }
            }
        }
        throw new RuntimeException("В листе c тимпом Node нет объектов Line, которые бы являлись точкой.");
    }

    public static boolean isPoint(Line line) {
        return line.getStartX() == line.getEndX() && line.getStartY() == line.getEndY();
    }

    public static List<List<BezierPoint>> getPoints(List<BezierPoint> points, Double moment, Double duration) {
        List<List<BezierPoint>> emptyPoints = getEmptyPoints(points);
        int size = emptyPoints.size();
        for (int i = 0; i < size - 1; i++) {
            List<BezierPoint> curr = emptyPoints.get(i);
            List<BezierPoint> next = emptyPoints.get(i + 1);
            throwIfSizeMismatch(curr, next);
            makeNextPoints(curr, next, moment, duration);
        }
        return emptyPoints;
    }

    public static List<Line> getPointsAsLines(List<BezierPoint> bezierPoints) {
        List<Line> points = new ArrayList<>();
        for (BezierPoint bezierPoint : bezierPoints) {
            Line pointAsLine = getPointAsLine(bezierPoint);
            points.add(pointAsLine);
        }
        return points;
    }

    private static void makeNextPoints(List<BezierPoint> curr, List<BezierPoint> next, Double moment, Double duration) {
        int currSize = curr.size();
        for (int i = 0; i < currSize - 1; i++) {
            BezierPoint currPoint1 = curr.get(i);
            BezierPoint currPoint2 = curr.get(i + 1);
            BezierPoint nextPoint = next.get(i);
            double timePart = moment / duration;
            double x = currPoint1.x * (1 - timePart) + timePart * currPoint2.x;
            double y = currPoint1.y * (1 - timePart) + timePart * currPoint2.y;
            nextPoint.x = x;
            nextPoint.y = y;
        }
    }

    private static void throwIfSizeMismatch(List<BezierPoint> curr, List<BezierPoint> next) {
        if (curr.size() - next.size() != 1) {
            throw new RuntimeException("Ошибка 1");
        }
    }



    private static Line getPointAsLine(BezierPoint bezierPoint1) {
        return getLine(bezierPoint1, bezierPoint1);
    }

    private static Line getLine(BezierPoint bezierPoint1, BezierPoint bezierPoint2) {
        return new Line(bezierPoint1.x, bezierPoint1.y, bezierPoint2.x, bezierPoint2.y);
    }

    private  static List<List<BezierPoint>> getEmptyPoints(List<BezierPoint> startPoints) {
        int size = startPoints.size();
        List<List<BezierPoint>> bezierPoints = new ArrayList<>();

        bezierPoints.add(startPoints);
        for (int i = size - 1; i >= 1; i--) {
            List<BezierPoint> points = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                points.add(new BezierPoint());
            }
            bezierPoints.add(points);
        }
        return bezierPoints;
    }



    static void move(List<BezierPoint> mainBezierPoints,
                     Function<BezierPoint, Double> get,
                     BiConsumer<BezierPoint, Double> set,
                     double liftLength,
                     BiFunction<Double, Double, Double> moving)
    {
        for (BezierPoint mainBezierPoint : mainBezierPoints) {
            set.accept(mainBezierPoint, moving.apply(get.apply(mainBezierPoint), liftLength));
        }
    }

    static BiFunction<Double, Double, Double> down(){
        return (aDouble, aDouble2) -> aDouble - aDouble2;
    }

    static BiFunction<Double, Double, Double> up(){
        return (aDouble, aDouble2) -> aDouble + aDouble2;
    }

    static BiFunction<Double, Double, Double> no(){
        return (aDouble, aDouble2) -> aDouble;
    }
}
