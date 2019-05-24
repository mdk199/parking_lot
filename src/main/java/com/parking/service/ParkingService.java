package com.parking.service;

import com.parking.exception.ManagerException;
import com.parking.exception.ServiceException;
import com.parking.response.ParkingStatus;

import java.util.List;

public interface ParkingService {
    Integer park(String registrationNumber, String color) throws ServiceException;

    Integer park(String registrationNumber, String color, Integer entryPoint) throws ServiceException;

    Integer remove(Integer slotNumber) throws ServiceException;

    Integer getSlotNumber(String registrationNumber) throws ServiceException;

    List<Integer> getSlotNumbers(String color) throws ServiceException;

    List<String> getRegistrationNumbers(String color) throws ServiceException;

    List<ParkingStatus> getStatus();
}
