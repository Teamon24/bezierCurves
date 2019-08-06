package com.home.math.bezierCurve;

import java.util.ArrayList;

public class TwoDimArrListWithVerticalOccupancy<T> extends ArrayList<T> {

    private int row = 0;
    private int column = 0;
    private int rowCount = 10;
    private int columnCount = 10;

    public TwoDimArrListWithVerticalOccupancy() {
    }

    public TwoDimArrListWithVerticalOccupancy(int rowCount, int columnCount) {
        super(rowCount*columnCount);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        for (int i = 0; i < rowCount * columnCount; i++) {
            super.add(null);
        }
    }

    @Override
    public boolean add(T t) {
        try {
            final int row = this.getRow();
            final int column = this.getColumn();
            final int index = this.currentIndex(row, column);
            super.set(index, t);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public int getRow() {
        if (column > columnCount - 1) {
            //TODO - выброс исключения - переполнение или сдвиг назад на один элемент.
            return row = 0;
        }
        return row++;
    }

    public int getColumn() {
        if (row > rowCount - 1) {
            row = 0;
            return column++;
        }
        return column;
    }

    public void checkPlacement() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                stringBuilder.append(this.getEl(row, column));
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    private int currentIndex(int row, int column) {
        final int elementsOfFullRows = row * columnCount;
        return elementsOfFullRows + column;
    }

    private String getEl(int row, int column) {
        final int index = this.currentIndex(row, column);
        final T t = this.get(index);
        return t == null ? "." : "x";
    }

    public static void main(String[] args) {
        TwoDimArrListWithVerticalOccupancy<Object> twoDimArrList = new TwoDimArrListWithVerticalOccupancy(8,5);

        for (int i = 0; i < twoDimArrList.rowCount * twoDimArrList.columnCount; i++) {
            twoDimArrList.add(new Object());
            twoDimArrList.checkPlacement();
        }
        twoDimArrList.add(new Object());
    }
}
