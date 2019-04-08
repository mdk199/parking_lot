package com.main;

import com.exception.ParkingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Runner {

    private final static String SPLITTER = " ";

    private final static String QUIT = "quit";

    private static Scanner inputReader;

    private static CommandExecutor commandExecutor = new CommandExecutor();

    public static void main(String[] args) {
        String inputFileName = null;
        if (args.length > 0)
            inputFileName = args[0];

        if (Objects.nonNull(inputFileName)) {
            try {
                inputReader = new Scanner(new File(inputFileName));
                while (inputReader.hasNextLine()) {
                    String command = inputReader.nextLine();
                    try {
                        commandExecutor.execute(command.split(SPLITTER));
                    } catch (ParkingException e) {
                        System.out.println(e.getMessage());
                    }
                }
                inputReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("FILE NOT FOUND " + inputFileName);
            }
        } else {
            inputReader = new Scanner(System.in);
            while (inputReader.hasNextLine()) {
                String command = inputReader.nextLine();
                if (QUIT.equalsIgnoreCase(command))
                    break;

                try {
                    commandExecutor.execute(command.split(SPLITTER));
                } catch (ParkingException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
