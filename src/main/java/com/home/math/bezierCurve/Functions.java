package com.home.math.bezierCurve;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class Functions {

    public static Function<Double, Pair<Double, Double>> circle(final Double R) {
        return (angle) -> Pair.of(R * Math.cos(angle), R * Math.sin(angle));
    }

    public static Function<Double, Pair<Double, Double>> focus(final Double R, final Double O, final Double b) {
        return (t) -> {
            final double r = R * Math.exp(-1 * b * t);
            return Pair.of(
                    r * Math.cos(O * t),
                    r * Math.sin(O * t)
            );
        };
    }
}
