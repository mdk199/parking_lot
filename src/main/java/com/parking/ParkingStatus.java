package com.parking;

public class ParkingStatus {

    private Long slotNumber;

    private String registrationNumber;

    private String colour;

    public ParkingStatus(Long slotNumber, String registrationNumber, String colour) {
        this.slotNumber = slotNumber;
        this.registrationNumber = registrationNumber;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingStatus)) return false;

        ParkingStatus that = (ParkingStatus) o;

        if (slotNumber != null ? !slotNumber.equals(that.slotNumber) : that.slotNumber != null) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;
        return colour != null ? colour.equals(that.colour) : that.colour == null;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(slotNumber).append("   ")
                .append(registrationNumber).append("   ")
                .append(colour).toString();
    }
}
