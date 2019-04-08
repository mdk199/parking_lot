package com.parking;

public class ParkingSpace {

    private Long slotNumber;

    private boolean isVacant;

    private Vehicle vehicle;

    private ParkingType parkingType;


    public ParkingSpace(Long slotNumber, ParkingType parkingType) {
        this.slotNumber = slotNumber;
        this.parkingType = parkingType;
        this.isVacant = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpace setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isVacant = false;
        return this;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public ParkingSpace setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
        return this;
    }

    public Long getSlotNumber() {
        return slotNumber;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void clear(){
        this.vehicle = null;
        this.isVacant = true;
    }
}