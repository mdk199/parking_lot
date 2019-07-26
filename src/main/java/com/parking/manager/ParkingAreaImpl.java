package com.parking.manager;

import com.parking.entity.Slot;
import com.parking.entity.Type;
import com.parking.entity.Vehicle;
import com.parking.exception.ManagerException;
import com.parking.exception.ErrorMessage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ParkingAreaImpl class denotes parking area with an assumption of equidistant entry points
 */

public class ParkingAreaImpl implements ParkingArea{
    private final String DEFAULT_PARKING_TYPE = "CAR";

    Slot[] parkingSlots;

    Map<String, Integer> registrationNumberToSlot = new HashMap<>();

    int entryPoints;

    public ParkingAreaImpl(int slots, int entryPoints) {
        this.parkingSlots = new Slot[slots];
        for (int i = 0; i < slots; i++)
            parkingSlots[i] = new Slot(i + 1, new Type(DEFAULT_PARKING_TYPE));
        this.entryPoints = entryPoints;
    }

    @Override
    public void remove(int slotNumber) throws ManagerException {
        Vehicle vehicle = parkingSlots[slotNumber - 1].getVehicle();
        if (Objects.isNull(vehicle))
            throw new ManagerException(ErrorMessage.VEHICLE_NOT_FOUND);
        registrationNumberToSlot.remove(vehicle.getRegistrationNumber());
        parkingSlots[slotNumber - 1].clear();
    }

    @Override
    public int park(String registrationNumber, String color, int entryPoint) throws ManagerException {
        synchronized (this) {
            Vehicle vehicle = new Vehicle(registrationNumber, color);
            int slot = this.findNearestVacantSlot(entryPoint);
            if (slot < 0)
                throw new ManagerException(ErrorMessage.PARKING_SPACE_LIMIT_EXCEEDED);
            parkingSlots[slot].setVehicle(vehicle);
            registrationNumberToSlot.put(registrationNumber.toUpperCase(), slot + 1);
            return slot + 1;
        }
    }

    @Override
    public int getSlotNumber(String registrationNumber) throws ManagerException {
        if (Objects.isNull(registrationNumberToSlot.get(registrationNumber.toUpperCase())))
            throw new ManagerException(ErrorMessage.VEHICLE_NOT_FOUND);
        return registrationNumberToSlot.get(registrationNumber.toUpperCase());
    }

    @Override
    public List<Integer> getSlotNumbers(String color) {
        return Arrays.stream(parkingSlots)
                .filter(slot -> !slot.isVacant() && slot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(slot -> slot.getSlotNumber())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRegistrationNumbers(String color) {
        return Arrays.stream(parkingSlots)
                .filter(slot -> !slot.isVacant() && slot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(slot -> slot.getVehicle().getRegistrationNumber())
                .collect(Collectors.toList());
    }

    @Override
    public List<Slot> getStatus() {
        return Arrays.stream(parkingSlots)
                .filter(slot -> !slot.isVacant())
                .collect(Collectors.toList());
    }

    private int findNearestVacantSlot(int entryPoint) {
        int start = (entryPoints / parkingSlots.length) * (entryPoint - 1);
        int i = start;
        int j = i > 0 ? i - 1 : i;
        while (j >= 0) {
            if (parkingSlots[j].isVacant())
                break;
            j--;
        }

        while (i < parkingSlots.length) {
            if (parkingSlots[i].isVacant())
                break;
            i++;
        }

        int leftSearchDistance = (j > 0) ? Math.abs(start - j) : Integer.MAX_VALUE;
        int rightSearchDistance = (i < parkingSlots.length) ? Math.abs(start - i) : Integer.MAX_VALUE;
        if (leftSearchDistance == rightSearchDistance)
            return -1;
        return leftSearchDistance < rightSearchDistance ? j : i;
    }
}
