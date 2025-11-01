package ui;

import service.ProfileService;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleInterface {

    private static final Integer MAX_ERROR_COUNT = 3;
    private static final Logger LOGGER = Logger.getLogger(ConsoleInterface.class.getName());
    private final ProfileService profileService;

    public ConsoleInterface(ProfileService profileService) {
        this.profileService = profileService;
    }

    public void start() {
        printMenu();
        processCommands();
    }

    private void processCommands() {
        int errorCount = 0;
        try (Scanner scanner = new Scanner(System.in)) {

            while (errorCount < MAX_ERROR_COUNT) {
                System.out.println("Enter command:");
                try {
                    handleCommand(scanner);
                } catch (IllegalArgumentException exception) {
                    LOGGER.log(Level.WARNING, "Invalid command", exception);
                    errorCount++;
                }
            }
        }
    }

    private void handleCommand(Scanner scanner) {
        Command nextCommand = Command.valueOf(scanner.nextLine().toUpperCase());

        switch (nextCommand) {
            case LOADFILE -> {
                System.out.println("Enter file name:");
                String fileName = scanner.nextLine();
                profileService.loadFile("src/Data/" + fileName);
            }
            case SEARCH -> {
                System.out.println("Enter full name:");
                String fio = scanner.nextLine();
                profileService.searchProfileByFio(fio);
            }
        }
    }

    private void printMenu() {
        System.out.println(Arrays.toString(Command.values()));
    }

}
