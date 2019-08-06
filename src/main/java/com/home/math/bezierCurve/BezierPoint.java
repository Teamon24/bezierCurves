package com.home.math.bezierCurve;


public class BezierPoint {

    public Double x;
    public Double y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public BezierPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public BezierPoint() {
        this.x = 0d;
        this.y = 0d;
    }
}
