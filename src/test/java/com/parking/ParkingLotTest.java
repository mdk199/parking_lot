package com.parking;


import com.exception.ParkingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {


    private static final Long CAPACITY = 6l;

    private ParkingLot parkingLot;

    @Before
    public void setUp() throws ParkingException {
        parkingLot = new ParkingLot(CAPACITY);
    }

    @Test
    public void shouldCreateParkingLotOnInstantiation() throws ParkingException {
        ParkingLot parkingLot = new ParkingLot(CAPACITY);

        Assert.assertNotNull(parkingLot);
        Assert.assertTrue(CAPACITY.equals(parkingLot.getCapacity()));
    }

    @Test(expected = ParkingException.class)
    public void shouldThrowInvalidCapacityException() throws ParkingException {
        final long INVALID_CAPACITY = -1;
        ParkingLot parkingLot = new ParkingLot(INVALID_CAPACITY);
    }

    @Test
    public void shouldParkVehicleWithValidDetails() throws ParkingException {
        final String registrationNumber = "KA-2028-23-023";
        final String colour = "white";
        Long expectedSlot = 1L;
        Long parkSlot = parkingLot.park(registrationNumber, colour);

        Assert.assertEquals(expectedSlot, parkSlot);
    }

    @Test(expected = ParkingException.class)
    public void shouldThrowExceptionWhenParkWithInvalidVehicleDetails() throws ParkingException {
        final String registrationNumber = null, colour = null;
        Long parkSlot = parkingLot.park(registrationNumber, colour);
    }

    @Test(expected = ParkingException.class)
    public void shouldThrowExceptionIfParkingWithSameRegistrationNumber() throws ParkingException {
        final String registrationNumber = "KA-2028-23-023";
        final String colour_v1 = "white";
        final String colour_v2 = "black";
        parkingLot.park(registrationNumber, colour_v1);
        parkingLot.park(registrationNumber, colour_v2);
    }

    @Test
    public void shouldParkAndRemoveVehicleFromSlotProperly() throws ParkingException {
        final String registrationNumber = "KA-2028-23-043";
        final String colour = "black";
        Long parkedSlot = parkingLot.park(registrationNumber, colour);
        Long freedSlot = parkingLot.remove(parkedSlot);

        Assert.assertTrue(parkedSlot.equals(freedSlot));
    }

    @Test
    public void shouldProvideOverallParkingStatus() throws ParkingException{
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "cyan", "red"};
        List<ParkingStatus> parkingStatuses = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
          Long slot =  parkingLot.park(registrationNumber[i], colour[i]);
          parkingStatuses.add(new ParkingStatus(slot, registrationNumber[i], colour[i]));
        }

        Assert.assertTrue(parkingLot.getStatus().containsAll(parkingStatuses));
    }

    @Test
    public void shouldProvideSlotNumbersIfQueriedOnVehicleColour() throws ParkingException{
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "black", "red"};
        List<Long> expectedBlackSlots = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
            Long slot =  parkingLot.park(registrationNumber[i], colour[i]);
            if(colour[i].equalsIgnoreCase("Black"))
                expectedBlackSlots.add(slot);
        }
        List<Long> actualBlackSlots = parkingLot.getSlotNumbers("Black");

        Assert.assertEquals(expectedBlackSlots.size(), actualBlackSlots.size());
        Assert.assertTrue(expectedBlackSlots.containsAll(actualBlackSlots));
    }

    @Test
    public void shouldProvideRegistrationNumbersIfQueriedOnVehicleColour() throws ParkingException{
        final String[] registrationNumber = {"KA-2028-23-040", "MP-2028-23-041", "WB-2028-23-042", "RJ-2028-23-043"};
        final String[] colour = {"black", "white", "black", "red"};
        List<String> expectedBlackRegNumber = new ArrayList<>();
        for (int i = 0; i < registrationNumber.length; i++) {
            Long slot =  parkingLot.park(registrationNumber[i], colour[i]);
            if(colour[i].equalsIgnoreCase("Black"))
                expectedBlackRegNumber.add(registrationNumber[i]);
        }
        List<String> actualBlackRegNumber = parkingLot.getRegistrationNumbers("Black");

        Assert.assertEquals(expectedBlackRegNumber.size(), actualBlackRegNumber.size());
        Assert.assertTrue(expectedBlackRegNumber.containsAll(actualBlackRegNumber));
    }

    @Test
    public void shouldProvideSlotNumberIfQueriedOnVehicleRegistrationNumber() throws ParkingException{
        final String registrationNumber = "RJ-2028-23-043";
        final String colour = "black";
        Long expectedParkedSlot = parkingLot.park(registrationNumber,colour);
        Long actualParkedSlot = parkingLot.getSlotNumber(registrationNumber);

        Assert.assertEquals(expectedParkedSlot, actualParkedSlot);
    }
}
