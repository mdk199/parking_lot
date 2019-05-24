package com.parking;


import com.parking.exception.ServiceException;
import com.parking.response.ParkingStatus;
import com.parking.service.ParkingService;
import com.parking.service.ParkingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingServiceTest {

    private static final Integer CAPACITY = 6;

    private ParkingService parkingService;

    @Before
    public void setUp() throws ServiceException {
        parkingService = new ParkingServiceImpl(CAPACITY);
    }

    @Test
    public void shouldCreateParkingLotOnInstantiation() throws ServiceException {
        ParkingService parkingService = new ParkingServiceImpl(CAPACITY);
        Assert.assertNotNull(parkingService);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowInvalidCapacityException() throws ServiceException {
        final int INVALID_CAPACITY = -1;
        ParkingServiceImpl parkingServiceImpl = new ParkingServiceImpl(INVALID_CAPACITY);
    }

    @Test
    public void shouldParkVehicleWithValidDetails() throws ServiceException {
        final String registrationNumber = "KA-2028-23-023";
        final String colour = "white";
        Integer expectedSlot = 1;
        Integer parkSlot = parkingService.park(registrationNumber, colour);

        Assert.assertEquals(expectedSlot, parkSlot);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenParkWithInvalidVehicleDetails() throws ServiceException {
        final String registrationNumber = null, colour = null;
        Integer parkSlot = parkingService.park(registrationNumber, colour);
    }

    @Test
    public void shouldParkAndRemoveVehicleFromSlotProperly() throws ServiceException {
        final String registrationNumber = "KA-2028-23-043";
        final String colour = "black";
        Integer parkedSlot = parkingService.park(registrationNumber, colour);
        Integer freedSlot = parkingService.remove(parkedSlot);

        Assert.assertTrue(parkedSlot.equals(freedSlot));
    }

    @Test
    public void shouldProvideOverallParkingStatus() throws ServiceException {
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "cyan", "red"};
        List<ParkingStatus> parkingStatuses = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
            Integer slot = parkingService.park(registrationNumber[i], colour[i]);
            parkingStatuses.add(new ParkingStatus(slot, registrationNumber[i], colour[i]));
        }
        Assert.assertTrue(parkingService.getStatus().containsAll(parkingStatuses));
    }

    @Test
    public void shouldProvideSlotNumbersIfQueriedOnVehicleColour() throws ServiceException {
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "black", "red"};
        List<Integer> expectedBlackSlots = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
            Integer slot = parkingService.park(registrationNumber[i], colour[i]);
            if (colour[i].equalsIgnoreCase("Black"))
                expectedBlackSlots.add(slot);
        }
        List<Integer> actualBlackSlots = parkingService.getSlotNumbers("Black");

        Assert.assertEquals(expectedBlackSlots.size(), actualBlackSlots.size());
        Assert.assertTrue(expectedBlackSlots.containsAll(actualBlackSlots));
    }

    @Test
    public void shouldProvideRegistrationNumbersIfQueriedOnVehicleColour() throws ServiceException {
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "black", "red"};
        List<String> expectedBlackRegNumber = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
            Integer slot = parkingService.park(registrationNumber[i], colour[i]);
            if (colour[i].equalsIgnoreCase("Black"))
                expectedBlackRegNumber.add(registrationNumber[i]);
        }
        List<String> actualBlackRegNumber = parkingService.getRegistrationNumbers("Black");

        Assert.assertEquals(expectedBlackRegNumber.size(), actualBlackRegNumber.size());
        Assert.assertTrue(expectedBlackRegNumber.containsAll(actualBlackRegNumber));
    }

    @Test
    public void shouldProvideSlotNumberIfQueriedOnVehicleRegistrationNumber() throws ServiceException {
        final String registrationNumber = "RJ-2028-23-043";
        final String colour = "black";
        Integer expectedParkedSlot = parkingService.park(registrationNumber, colour);
        Integer actualParkedSlot = parkingService.getSlotNumber(registrationNumber);

        Assert.assertEquals(expectedParkedSlot, actualParkedSlot);
    }
}
