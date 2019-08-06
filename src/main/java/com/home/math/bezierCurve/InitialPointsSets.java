package com.home.math.bezierCurve;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InitialPointsSets {

    private static final double WHOLE = Main.WHOLE;

    public static List<BezierPoint> focus() {
        final Function<Double, Pair<Double, Double>> focus = Functions.focus(WHOLE / 2d, 2d, 0.1);
        final Pair<Double, Double> interval = Pair.of(0d, 20d);
        List<BezierPoint> startPoints = InitialPointsSets.points(focus, 50, interval);
        return startPoints;
    }

    public static List<BezierPoint> random(final int amount) {
        List<BezierPoint> startPoints = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            startPoints.add(new BezierPoint(RandomUtils.nextDouble(0, WHOLE / 1.5 ), RandomUtils.nextDouble(0, WHOLE / 1.5 )));
        }
        return startPoints;
    }

    public static List<BezierPoint> points(final Function<Double, Pair<Double, Double>> function,
                                           final Integer pointsAmount,
                                           final Pair<Double, Double> interval)
    {

        List<Double> doubles = getPointsFromInterval(pointsAmount, interval);

        List<Pair<Double, Double>> pointsValues = Lists.newArrayList();
        for (Double aDouble : doubles) {
            final Pair<Double, Double> point = function.apply(aDouble);
            pointsValues.add(point);
        }

        List<BezierPoint> startPoints = getBezierPoints(pointsValues);
        return startPoints;
    }

    private static List<Double> getPointsFromInterval(final Integer pointsAmount,
                                                      final Pair<Double, Double> interval)
    {
        final ArrayList<Double> points = new ArrayList<>();

        final Double end = interval.getRight();
        final Double start = interval.getLeft();
        final double step = (end - start) / pointsAmount;

        for (int i = 0; i < pointsAmount; i++) {
            points.add(start + i * step);
        }
        return points;
    }

    private static List<BezierPoint> getBezierPoints(final List<Pair<Double, Double>> pointsValues)
    {
        List<BezierPoint> a = new ArrayList<>();
        for (Pair<Double, Double> pointsValue : pointsValues) {
            a.add(new BezierPoint(pointsValue.getLeft(), pointsValue.getRight()));
        }
        return a;
    }
}
