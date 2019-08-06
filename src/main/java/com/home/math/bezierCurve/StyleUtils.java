package com.home.math.bezierCurve;

import com.google.common.collect.Lists;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Collections;
import java.util.List;

public class StyleUtils {

    public static void setGradientColorStroke(List<List<Line>> linesList, double strokeWidth) {
        int size = linesList.size();
        for (int i = 0; i < size; i++) {
            Color color = Color.color((double) i /size - 0.5 < 0 ? (double) i /size : (double) i /size - 0.5, 0, 0);
            setStrokeAndColor(linesList, strokeWidth, i, color);
        }
    }

    public static void setColorAndStroke(List<List<Line>> linesList, Color color, double strokeWidth) {
        int size = linesList.size();
        for (int i = 0; i < size; i++) {
            setStrokeAndColor(linesList, strokeWidth, i, color);
        }
    }

    public static void setColorAndStroke(Line line, Color color, double strokeWidth) {
        final List<List<Line>> lines = Lists.newArrayList();
        lines.add(Collections.singletonList(line));
        setStrokeAndColor(lines, strokeWidth, 0, color);
    }

    private static void setStrokeAndColor(List<List<Line>> linesList, double strokeWidth, int i, Color color) {
        List<Line> lines = linesList.get(i);
        for (Line line : lines) {
            if (BezierUtils.isPoint(line)) {
                line.setStrokeWidth(strokeWidth);
            }
            line.setStroke(color);
        }
    }


}
