package com.parking.entity;

public class Slot {

    private int slotNumber;

    private boolean isVacant;

    private Vehicle vehicle;

    private Type type;


    public Slot(int slotNumber, Type type) {
        this.slotNumber = slotNumber;
        this.type = type;
        this.isVacant = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Slot setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isVacant = false;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Slot setType(Type type) {
        this.type = type;
        return this;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void clear() {
        this.vehicle = null;
        this.isVacant = true;
    }
}