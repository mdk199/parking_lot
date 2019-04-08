package com.parking;


/**
 * Identify the parking space type
 */
public class ParkingType {

    private String type;

    private ParkingDimension parkingDimension;

    public ParkingType(String type) {
        this.type = type;
    }

    public ParkingType(String type, ParkingDimension parkingDimension) {
        this.type = type;
        this.parkingDimension = parkingDimension;
    }

    public String getType() {
        return type;
    }

    public ParkingDimension getParkingDimension() {
        return parkingDimension;
    }
}
