package com.main;

import com.parking.exception.ManagerException;
import com.parking.exception.ServiceException;
import com.parking.service.ParkingService;
import com.parking.service.ParkingServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.parking.exception.ErrorMessage.*;

public class CommandExecutor {

    private final static String DELIMITER = ", ";
    ParkingService parkingService = null;

    public void execute(String[] args) throws Exception {
        switch (args[0]) {
            case "create_parking_lot":
                if (args.length < 3)
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<capacity> <entry_point>");

                try {
                    parkingService = new ParkingServiceImpl(Integer.parseInt(args[1]));
                    System.out.println("Created a parking lot with " + args[1] + " slots");
                } catch (NumberFormatException e) {
                    throw new ServiceException(INVALID_CAPACITY);
                }

                break;

            case "leave":
                if (args.length < 2)
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<slot_number>");

                if (Objects.isNull(parkingService))
                    throw new ServiceException(PARKING_LOT_NOT_FOUND);

                try {
                    parkingService.remove(Integer.parseInt(args[1]));
                    System.out.println("Slot number " + args[1] + " is free");
                } catch (NumberFormatException e) {
                    System.out.println(INVALID_SLOT_NUMBER);
                }
                break;

            case "park":
                if (args.length < 4)
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<registration_number> <colour> <entry_point>");

                if (Objects.isNull(parkingService))
                    throw new ServiceException(PARKING_LOT_NOT_FOUND);

                Integer slotNumber = parkingService.park(args[1], args[2], Integer.parseInt(args[3]));
                System.out.println("Allocated slot number: " + slotNumber);
                break;

            case "registration_numbers_for_cars_with_colour":
                if (args.length < 2)
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<colour>");

                if (Objects.isNull(parkingService))
                    throw new ServiceException(PARKING_LOT_NOT_FOUND);

                List<String> registrationNumbers = parkingService.getRegistrationNumbers(args[1]);
                System.out.println(String.join(DELIMITER, registrationNumbers));
                break;

            case "slot_numbers_for_cars_with_colour":
                if (args.length < 2)
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<colour>");

                if (Objects.isNull(parkingService))
                    throw new ServiceException(PARKING_LOT_NOT_FOUND);

                List<Integer> colours = parkingService.getSlotNumbers(args[1]);
                System.out.println(colours.stream()
                        .map(colour -> String.valueOf(colour))
                        .collect(Collectors.joining(DELIMITER)));
                break;

            case "slot_number_for_registration_number":
                if (args.length < 2) {
                    throw new ServiceException(INVALID_COMMAND_FORMAT, args[0], "<colour>");
                }
                System.out.println(parkingService.getSlotNumber(args[1]));
                break;

            case "status":
                if (Objects.isNull(parkingService))
                    throw new ManagerException(PARKING_LOT_NOT_FOUND);
                System.out.println("Slot No.    Registration No    Colour");
                parkingService.getStatus()
                        .forEach(status -> System.out.println(status.toString()));
                break;
        }
    }
}
