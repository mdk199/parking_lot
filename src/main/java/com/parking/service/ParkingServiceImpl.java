package com.parking.service;

import com.parking.exception.ManagerException;
import com.parking.exception.ServiceException;
import com.parking.manager.ParkingArea;
import com.parking.manager.ParkingAreaImpl;
import com.parking.response.ParkingStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.parking.exception.ErrorMessage.*;

public class ParkingServiceImpl implements ParkingService{

    private ParkingArea parkingArea;

    private int slots;

    private int entryPoints = 1;

    private int DEFAULT_ENTRY_POINT = 1;

    public ParkingServiceImpl(int capacity) throws ServiceException {
        checkValidCapacity(capacity);
        this.slots = capacity;
        this.parkingArea = new ParkingAreaImpl(slots, entryPoints);
    }

    public ParkingServiceImpl(int capacity, int entryPoints) throws ServiceException{
        checkValidCapacity(capacity);
        this.slots = capacity;
        this.entryPoints = entryPoints;
        this.parkingArea = new ParkingAreaImpl(slots, entryPoints);
    }

    @Override
    public Integer park(String registrationNumber, String color) throws ServiceException {
        return this.park(registrationNumber, color, DEFAULT_ENTRY_POINT);
    }

    @Override
    public Integer park(String registrationNumber, String color, Integer entryPoint) throws ServiceException {
        if (Objects.isNull(registrationNumber))
            throw new ServiceException(INVALID_VEHICLE_REGISTRATION_NUMBER, registrationNumber);

        if (Objects.isNull(color))
            throw new ServiceException(INVALID_VEHICLE_COLOUR, color);

        Integer slot;
        try {
            slot = parkingArea.park(registrationNumber, color, entryPoint);
        } catch (ManagerException e) {
            throw new ServiceException(e.getMessage());
        }
        return slot;
    }

    @Override
    public Integer remove(Integer slotNumber) throws ServiceException {
        if (Objects.isNull(slotNumber) || slotNumber <= 0)
            throw new ServiceException(INVALID_SLOT_NUMBER, slotNumber);
        try {
            parkingArea.remove(slotNumber);
        } catch (ManagerException e) {
            throw new ServiceException(e.getMessage());
        }
        return slotNumber;
    }

    @Override
    public Integer getSlotNumber(String registrationNumber) throws ServiceException {
        if (Objects.isNull(registrationNumber))
            throw new ServiceException(INVALID_VEHICLE_REGISTRATION_NUMBER, registrationNumber);

        Integer slotNumber = null;
        try {
            slotNumber = parkingArea.getSlotNumber(registrationNumber);
        } catch (ManagerException e) {
            throw new ServiceException(e.getMessage());
        }
        return slotNumber;
    }

    @Override
    public List<Integer> getSlotNumbers(String color) throws ServiceException {
        if (Objects.isNull(color))
            throw new ServiceException(INVALID_VEHICLE_COLOUR, color);
        return parkingArea.getSlotNumbers(color);
    }

    @Override
    public List<String> getRegistrationNumbers(String color) throws ServiceException {
        if (Objects.isNull(color))
            throw new ServiceException(INVALID_VEHICLE_COLOUR, color);
        return parkingArea.getRegistrationNumbers(color);
    }

    @Override
    public List<ParkingStatus> getStatus() {
        return parkingArea.getStatus()
                .stream()
                .map(slot -> new ParkingStatus(slot.getSlotNumber()
                        , slot.getVehicle().getRegistrationNumber()
                        , slot.getVehicle().getColor()))
                .collect(Collectors.toList());
    }

    private void checkValidCapacity(int capacity) throws ServiceException{
        if(capacity <= 0)
            throw new ServiceException(INVALID_CAPACITY, capacity);
    }
}


