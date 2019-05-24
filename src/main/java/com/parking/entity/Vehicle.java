package com.parking.entity;

public class Vehicle {

    private String registrationNumber;

    private String color;

    private String owner;

    public Vehicle(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public Vehicle(String registrationNumber, String color, String owner) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.owner = owner;
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
