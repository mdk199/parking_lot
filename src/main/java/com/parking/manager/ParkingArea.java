package com.parking.manager;

import com.parking.entity.Slot;
import com.parking.exception.ManagerException;

import java.util.List;

public interface ParkingArea {
    void remove(int slotNumber) throws ManagerException;

    int park(String registrationNumber, String color, int entryPoint) throws ManagerException;

    int getSlotNumber(String registrationNumber) throws ManagerException;

    List<Integer> getSlotNumbers(String color);

    List<String> getRegistrationNumbers(String color);

    List<Slot> getStatus();
}
