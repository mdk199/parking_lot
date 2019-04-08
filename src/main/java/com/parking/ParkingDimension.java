package com.parking;

public class ParkingDimension {

    private Long length;

    private Long width;

    private Long height;

    public ParkingDimension(Long length, Long width, Long height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Long getLength() {
        return length;
    }

    public Long getWidth() {
        return width;
    }

    public Long getHeight() {
        return height;
    }
}
