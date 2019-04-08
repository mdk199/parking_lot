package com.main;

import com.exception.ParkingException;
import com.parking.ParkingLot;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.exception.ErrorMessage.*;

public class CommandExecutor {

    private final static String DELIMITER = ", ";
    ParkingLot parkingLot = null;

    public void execute(String[] arguments) throws ParkingException {
        switch (arguments[0]) {
            case "create_parking_lot":
                if (arguments.length < 2)
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<capacity>");

                try {
                    parkingLot = new ParkingLot(Long.parseLong(arguments[1]));
                    System.out.println("Created a parking lot with " + arguments[1] + " slots");
                } catch (NumberFormatException e) {
                    throw new ParkingException(INVALID_CAPACITY);
                }

                break;

            case "leave":
                if (arguments.length < 2)
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<slot_number>");

                if (Objects.isNull(parkingLot))
                    throw new ParkingException(PARKING_LOT_NOT_FOUND);

                try {
                    Long slotNumber = parkingLot.remove(Long.parseLong(arguments[1]));
                    System.out.println("Slot number " + arguments[1] + " is free");
                } catch (NumberFormatException e) {
                    System.out.println(INVALID_SLOT_NUMBER);
                }
                break;

            case "park":
                if (arguments.length < 3)
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<registration_number> <colour>");

                if (Objects.isNull(parkingLot))
                    throw new ParkingException(PARKING_LOT_NOT_FOUND);

                Long slotNumber = parkingLot.park(arguments[1], arguments[2]);
                System.out.println("Allocated slot number: " + slotNumber);
                break;

            case "registration_numbers_for_cars_with_colour":
                if (arguments.length < 2)
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<colour>");

                if (Objects.isNull(parkingLot))
                    throw new ParkingException(PARKING_LOT_NOT_FOUND);

                List<String> registrationNumbers = parkingLot.getRegistrationNumbers(arguments[1]);
                System.out.println(String.join(DELIMITER, registrationNumbers));
                break;

            case "slot_numbers_for_cars_with_colour":
                if (arguments.length < 2)
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<colour>");

                if (Objects.isNull(parkingLot))
                    throw new ParkingException(PARKING_LOT_NOT_FOUND);

                List<Long> colours = parkingLot.getSlotNumbers(arguments[1]);
                System.out.println(colours.stream()
                        .map(colour -> String.valueOf(colour))
                        .collect(Collectors.joining(DELIMITER)));
                break;

            case "slot_number_for_registration_number":
                if (arguments.length < 2) {
                    throw new ParkingException(INVALID_COMMAND_FORMAT, arguments[0], "<colour>");
                }
                System.out.println(parkingLot.getSlotNumber(arguments[1]));
                break;

            case "status":
                if (Objects.isNull(parkingLot))
                    throw new ParkingException(PARKING_LOT_NOT_FOUND);
                System.out.println("Slot No.    Registration No    Colour");
                parkingLot.getStatus()
                        .forEach(status -> System.out.println(status.toString()));
                break;
        }
    }
}
