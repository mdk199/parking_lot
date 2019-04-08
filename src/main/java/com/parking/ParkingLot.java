package com.parking;

import com.exception.ParkingException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.exception.ErrorMessage.*;

public class ParkingLot {

    private final String DEFAULT_PARKING_TYPE = "CAR";

    private ParkingNode start;

    private Map<Long, ParkingNode> slotNumberToParkedSpace = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Long> registrationToSlotNumber = new ConcurrentHashMap<>();

    private long capacity;

    public ParkingLot(long capacity) throws ParkingException {
        this.capacity = capacity;
        this.createParkingLot();
    }

    public synchronized Long park(String registrationNumber, String color) throws ParkingException {
        if (Objects.isNull(registrationNumber))
            throw new ParkingException(INVALID_VEHICLE_REGISTRATION_NUMBER, registrationNumber);

        if (Objects.isNull(color))
            throw new ParkingException(INVALID_VEHICLE_COLOUR, color);

        if (Objects.nonNull(registrationToSlotNumber.get(registrationNumber.toUpperCase())))
            throw new ParkingException(VEHICLE_ALREADY_PARKED, registrationNumber);

        ParkingNode nearestVacant = findNearestVacant();
        if (Objects.isNull(nearestVacant)) {
            throw new ParkingException(PARKING_SPACE_LIMIT_EXCEEDED);
        }

        Vehicle vehicle = new Vehicle(registrationNumber, color);
        nearestVacant.getParkingSpace().setVehicle(vehicle);
        Long slotNumber = nearestVacant.getParkingSpace().getSlotNumber();
        slotNumberToParkedSpace.put(slotNumber, nearestVacant);
        registrationToSlotNumber.put(registrationNumber.toUpperCase(), slotNumber);

        return slotNumber;
    }

    public Long remove(Long slotNumber) throws ParkingException {
        ParkingNode parkedNode = slotNumberToParkedSpace.get(slotNumber);
        if (Objects.isNull(parkedNode))
            throw new ParkingException(VEHICLE_NOT_FOUND);

        String registrationNumber = parkedNode.getParkingSpace().getVehicle().getRegistrationNumber();
        parkedNode.getParkingSpace().clear();
        slotNumberToParkedSpace.remove(slotNumber);
        Long freedSlotNumber = registrationToSlotNumber.remove(registrationNumber);
        return freedSlotNumber;
    }


    public Long getSlotNumber(String registrationNumber) throws ParkingException {
        if(Objects.isNull(registrationNumber))
            throw new ParkingException(INVALID_VEHICLE_REGISTRATION_NUMBER, registrationNumber);

        Long slotNumber = registrationToSlotNumber.get(registrationNumber.toUpperCase());
        if (Objects.isNull(slotNumber))
            throw new ParkingException(VEHICLE_NOT_FOUND);

        return slotNumber;
    }

    public List<Long> getSlotNumbers(String color) throws ParkingException{
        if(Objects.isNull(color))
            throw new ParkingException(INVALID_VEHICLE_COLOUR, color);

        return slotNumberToParkedSpace.values()
                .stream()
                .filter(parkingNode -> parkingNode.getParkingSpace().getVehicle().getColor().equalsIgnoreCase(color))
                .map(parkingNode -> parkingNode.getParkingSpace().getSlotNumber())
                .collect(Collectors.toList());
    }

    public List<String> getRegistrationNumbers(String color) throws ParkingException{
        if(Objects.isNull(color))
            throw new ParkingException(INVALID_VEHICLE_COLOUR, color);

        return slotNumberToParkedSpace.values()
                .stream()
                .filter(parkingNode -> parkingNode.getParkingSpace().getVehicle().getColor().equalsIgnoreCase(color))
                .map(parkingNode -> parkingNode.getParkingSpace().getVehicle().getRegistrationNumber())
                .collect(Collectors.toList());
    }

    public List<ParkingStatus> getStatus() {
        return slotNumberToParkedSpace.values()
                .stream()
                .map(parkingNode -> new ParkingStatus(parkingNode.getParkingSpace().getSlotNumber()
                        , parkingNode.getParkingSpace().getVehicle().getRegistrationNumber()
                        , parkingNode.getParkingSpace().getVehicle().getColor()))
                .collect(Collectors.toList());
    }

    public long getCapacity() {
        return capacity;
    }

    private void createParkingLot() throws ParkingException {
        if (this.capacity <= 0l)
            throw new ParkingException(INVALID_CAPACITY, capacity);
        long slot = 1;

        start = new ParkingNode(new ParkingSpace(slot++, new ParkingType(DEFAULT_PARKING_TYPE)));
        ParkingNode temp = start;
        while (slot <= capacity) {
            temp.setNext(new ParkingNode(new ParkingSpace(slot++, new ParkingType(DEFAULT_PARKING_TYPE))));
            temp = temp.getNext();
        }
    }

    private ParkingNode findNearestVacant() {
        ParkingNode nearestVacant = null;
        ParkingNode temp = start;
        while (Objects.nonNull(temp)) {
            if (temp.getParkingSpace().isVacant()) {
                nearestVacant = temp;
                break;
            }
            temp = temp.next;
        }
        return nearestVacant;
    }

    private class ParkingNode {
        private ParkingSpace parkingSpace;

        private ParkingNode next;

        public ParkingNode(ParkingSpace parkingSpace) {
            this.parkingSpace = parkingSpace;
            this.next = null;
        }

        public ParkingSpace getParkingSpace() {
            return parkingSpace;
        }

        public ParkingNode getNext() {
            return next;
        }

        public ParkingNode setNext(ParkingNode next) {
            this.next = next;
            return this;
        }
    }
}


