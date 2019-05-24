package com.parking.entity;

public class Dimension {

    private long length;

    private long width;

    private long height;

    public Dimension(long length, long width, long height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public long getLength() {
        return length;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }
}
