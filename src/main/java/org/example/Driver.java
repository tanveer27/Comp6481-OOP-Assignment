package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * Assignment: #0 (part 2B)
 * © Tanveer Reza
 * Written by: Tanveer Reza, Student ID: 40292971
 * This is the driver class, it contains the main method, the display menu methods, the search menu, the print function and checkPassword function
 */
public class Driver {
    private static final String PASSWORD = "device2024"; // keeping the password as a constant
    private static SmartDeviceService smartDeviceService;

    /**
     * This is the main method, this is where the driver class starts
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Smart Device Inventory!!!");
        System.out.print("Please enter the maximum number of smart devices : ");
        Scanner scanner = new Scanner(System.in); // take the max number of devices as input
        int maxNumberOfSmartDevices = Integer.parseInt(scanner.nextLine());
        smartDeviceService = new SmartDeviceService(maxNumberOfSmartDevices); // initiate the smartDeviceService with max number of devices
        showMainMenu(scanner);
    }

    /**
     * This is the main menu display, this is responsible for showing the user the main menu
     * @param scanner
     */
    public static void showMainMenu(Scanner scanner) {
        int totalAttempts = 0;
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("\t 1. Add devices (password required).");
            System.out.println("\t 2. Update device (password required).");
            System.out.println("\t 3. Find devices by type.");
            System.out.println("\t 4. Find affordable devices.");
            System.out.println("\t 5. Quit");
            System.out.print("Please enter your choice > ");

            int choice = Integer.parseInt(scanner.nextLine()); // assuming the user will only enter a number between 0-9

            switch (choice) {
                case 1:
                    totalAttempts = checkPassword(scanner, totalAttempts, true); // make sure the program exits on 12th consecutive failed attempt
                    if (totalAttempts >= 3) {
                        break;
                    }
                    showSmartDeviceMenu(scanner); // show the smartDeviceMenu when this statement is reached
                    break;
                case 2:
                    totalAttempts = checkPassword(scanner, totalAttempts, false); // the program will not exit on consecutive failed attempts
                    if (totalAttempts >= 3) {
                        totalAttempts = 0; // reset the totalAttemptCount so that it does not count towards consecutive failed attempts in choice 1
                        break;
                    } else {
                        totalAttempts = 0; // reset the totalAttemptCount so that it does not count towards consecutive failed attempts in choice 1
                        showUpdateSmartDeviceMenu(scanner); // show Smart Device update menu
                    }
                    break;
                case 3:
                    totalAttempts = 0; // reset the totalAttemptCount so that it does not count towards consecutive failed attempts in choice 1
                    searchByType(scanner); // show the search option
                    break;
                case 4:
                    totalAttempts = 0; // reset the totalAttemptCount so that it does not count towards consecutive failed attempts in choice 1
                    showFindAffordableDeviceMenu(scanner); // show findAffordable devices menu
                    break;
                case 5:
                    System.out.println("Closing the program. Goodbye!"); // Close the program with a clear exist message
                    return;
                default:
                    totalAttempts = 0; // reset the totalAttemptCount so that it does not count towards consecutive failed attempts in choice 1
                    System.out.println("Invalid choice. Please select a number between 1 and 5."); // display clear error message when wrong choice entered
            }

        }
    }


    /**
     * This function is responsible for checking the password and returning the total attempts
     * It takes the current consecutive attempts as input and tries to take password three times in a row unless the correct password is given
     * If the exitOnSuspiciousAttempts boolean is true, if at any point the consecutive attempts reach 12. it displays an shutdown message and terminates the program
     * @param scanner
     * @param totalAttempts
     * @param exitOnSuspiciousAttempts
     * @return number of totalAttempts
     */
    private static int checkPassword(Scanner scanner, int totalAttempts, boolean exitOnSuspiciousAttempts) {
        int attempts = 0;
        // prompt the user for correct password 3 times unless the correct password is given
        while (attempts < 3) {
            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();
            if (PASSWORD.equals(inputPassword)) {
                return 0; // when correct password is entered reset the attempts
            } else {
                attempts++;
            }

            // if exitOnSuspiciousAttempts is true, check for 12 consecutive failed attempts and exit if it happens
            if (totalAttempts + attempts >= 12 && exitOnSuspiciousAttempts) {
                System.out.println("Program detected suspicious activities and will terminate immediately!");
                System.exit(0);
            }
        }
        return totalAttempts + attempts;  // Return with the total attempts with added incorrect attempts
    }

    /**
     * This menu is responsible for showing the add SmartDevice menu
     * @param scanner
     */
    public static void showSmartDeviceMenu(Scanner scanner) {
        System.out.print("Enter the number of new devices : ");
        int numberOfNewDevices = Integer.parseInt(scanner.nextLine()); // assume it will always be an integer
        int presentNumberOfSmartDevices = SmartDevice.getNumberOfSmartDevices(); // get the current number of smartDevices
        int availableNumber = smartDeviceService.getMaxNumberOfSmartDevices() - presentNumberOfSmartDevices; // get available smart device slots

        // add new smart devices if spaces are available, otherwise show the number of available devices
        if (numberOfNewDevices <= availableNumber) {
            for (int i = 0; i < numberOfNewDevices; i++) {
                SmartDevice smartDevice = new SmartDevice(); // create a new smart device with default values
                smartDeviceService.addSmartDevice(smartDevice); // add smart device to the device database array
            }
            System.out.println(numberOfNewDevices + " devices successfully added");
        } else {
            System.out.println("You can enter " + availableNumber + " device(s)");
        }
    }

    /**
     * This is the updateSmartDevice menu, this menu is responsible for displaying the update options
     * @param scanner
     */
    public static void showUpdateSmartDeviceMenu(Scanner scanner) {
        while (true) {
            System.out.print("Enter the device ID of the SmartDevice to update: ");
            Long deviceID = Long.parseLong(scanner.nextLine()); // assuming the users will enter a integer or long

            int indexOfSmartDevice = smartDeviceService.findDeviceIndexByID(deviceID); // try to get the device from deviceDatabase array
            if (indexOfSmartDevice != -1) {
                showUpdateDeviceAttributeMenu(scanner, smartDeviceService.getDeviceByIndex(indexOfSmartDevice), indexOfSmartDevice); // display the update menu when device found
                break;
            } else {
                System.out.println("No SmartDevice found with ID: " + deviceID + ", would you like to try again?");
                System.out.println("\t 1 : Yes");
                System.out.println("\t Any other number : No");
                System.out.print("Please enter your choice: ");

                int choice = Integer.parseInt(scanner.nextLine()); // assuming user will always enter an integer
                if (choice != 1) {
                    System.out.println("Returning to the main menu...");
                    break;
                }
            }
        }
    }

    /**
     * This is the search menu, it prompts the user for device type and prints the matching devices when found
     * @param scanner
     */
    private static void searchByType(Scanner scanner) {
        System.out.print("Enter the desired device type: ");
        String desiredType = scanner.nextLine();
        List<SmartDevice> matchingDevices = smartDeviceService.findSmartDevicesByType(desiredType); // retrieve the list of smart devices by type

        if (matchingDevices.isEmpty()) {
            System.out.println("No devices found of type: " + desiredType);
        } else {
            System.out.println("Devices found of type: " + desiredType);
            printSmartDevices(matchingDevices); // print smart devices from the list when list not empty
        }
    }

    /**
     * This is the deviceAttributeUpdateMenu, it shows device information with the index of device, and displays the update options
     * @param scanner
     * @param device
     * @param deviceIndex
     */
    private static void showUpdateDeviceAttributeMenu(Scanner scanner, SmartDevice device, int deviceIndex) {
        boolean updating = true;
        while (updating) {
            System.out.println("SmartDevice: # " + deviceIndex);
            System.out.println(device);

            System.out.println("What attribute do you want to update?");
            System.out.println("1. Device Name");
            System.out.println("2. Device Type");
            System.out.println("3. OS Version");
            System.out.println("4. Battery Life");
            System.out.println("5. Price");
            System.out.println("6. Availability");
            System.out.println("7. Quit");
            System.out.print("Please enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine()); // assuming the users will always enter an integer

            switch (choice) {
                case 1:
                    System.out.print("Enter new Device Name: ");
                    device.setDeviceName(scanner.nextLine()); // update device name
                    break;
                case 2:
                    System.out.print("Enter new Device Type: ");
                    device.setDeviceType(scanner.nextLine()); // update device type
                    break;
                case 3:
                    System.out.print("Enter new OS Version: ");
                    device.setOsVersion(scanner.nextLine()); // update device version
                    break;
                case 4:
                    System.out.print("Enter new Battery Life: ");
                    device.setBatteryLife(Integer.parseInt(scanner.nextLine())); // update device battery life
                    break;
                case 5:
                    System.out.print("Enter new Price: ");
                    device.setPrice(Float.parseFloat(scanner.nextLine())); // update device price
                    break;
                case 6:
                    System.out.print("Is the device in stock? (true/false): ");
                    device.setInStock(Boolean.parseBoolean(scanner.nextLine())); // update device availability
                    break;
                case 7:
                    updating = false; // exit update menu
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }

        }
    }

    /**
     * This method is responsible for the affordable device search and show menu
     * @param scanner
     */
    private static void showFindAffordableDeviceMenu(Scanner scanner) {
        System.out.print("Enter the maximum price: ");
        float maxPrice = Float.parseFloat(scanner.nextLine()); // assuming the users will enter a floating number
        List<SmartDevice> affordableDevices = smartDeviceService.findAffordableDevices(maxPrice); // retrieve the affordable devices from deviceDatabase

        if (affordableDevices.isEmpty()) {
            System.out.println("No devices found with a price less than or equal to: $" + maxPrice);
        } else {
            System.out.println("Devices found with a price less than or equal to: $" + maxPrice);
            printSmartDevices(affordableDevices); // print the list of affordable devices
        }
    }

    /**
     * This the print device function, it takes the list of devices to print as an input, and prints out their information
     * @param affordableDevices
     */
    private static void printSmartDevices(List<SmartDevice> affordableDevices) {
        for (SmartDevice device : affordableDevices) {
            System.out.println(device);
        }
    }
}