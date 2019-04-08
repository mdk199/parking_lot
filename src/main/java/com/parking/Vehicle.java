package com.parking;

public class Vehicle {

    private String registrationNumber;

    private String color;

    private String owner;

    private ParkingType parkingType;

    private final String DEFAULT_TYPE = "CAR";

    public Vehicle(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.parkingType = new ParkingType(DEFAULT_TYPE);
    }

    public Vehicle(String registrationNumber, String color, String owner) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.owner = owner;
        this.parkingType = new ParkingType(DEFAULT_TYPE);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public Vehicle setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;

        Vehicle vehicle = (Vehicle) o;

        if (registrationNumber != null ? !registrationNumber.equals(vehicle.registrationNumber) : vehicle.registrationNumber != null)
            return false;
        if (color != null ? !color.equals(vehicle.color) : vehicle.color != null) return false;
        return owner != null ? owner.equals(vehicle.owner) : vehicle.owner == null;
    }

    @Override
    public int hashCode() {
        int result = registrationNumber != null ? registrationNumber.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
