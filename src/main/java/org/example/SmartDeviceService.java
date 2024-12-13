package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: #0 (part 2B)
 * © Tanveer Reza
 * Written by: Tanveer Reza, Student ID: 40292971
 * This is the SmartDeviceService class, it manages the deviceDatabase array, the user provided input for the database array and operations on the array
 * This class can be considered as a database service class for our program
 */
public class SmartDeviceService {
    private SmartDevice[] deviceDataBase;
    private int maxNumberOfSmartDevices;
    private int currentDeviceCount;

    /**
     * this is the constructor of this class, it accepts the number of max number of smart devices and sets up our deviceDatabase
     * @param maxNumberOfSmartDevices
     */
    public SmartDeviceService(int maxNumberOfSmartDevices) {
        this.currentDeviceCount = 0;
        this.maxNumberOfSmartDevices = maxNumberOfSmartDevices;
        this.deviceDataBase = new SmartDevice[maxNumberOfSmartDevices];
    }

    /**
     * the function returns the max Number of SmartDevices
     * @return maxNumberOfSmartDevices
     */
    public int getMaxNumberOfSmartDevices() {
        return maxNumberOfSmartDevices;
    }

    /**
     * This function adds the smart device to the database
     * @param device
     */
    public void addSmartDevice(SmartDevice device) {
        deviceDataBase[currentDeviceCount++] = device;
    }

    /**
     * This function accepts the device type as param and returns the matching smartDevices from the database, the search is case insensitive
     * @param deviceType
     * @return listOf SmartDevices
     */
    public List<SmartDevice> findSmartDevicesByType(String deviceType) {
        List<SmartDevice> matchingDevices = new ArrayList<>();
        for (SmartDevice device : deviceDataBase) {
            if (device != null && device.getDeviceType().equalsIgnoreCase(deviceType)) {
                matchingDevices.add(device);
            }
        }
        return matchingDevices;
    }

    /**
     * This function accepts a price and returns all smartDevices within the price range
     * @param maxPrice
     * @return listOfSmartDevices
     */
    public List<SmartDevice> findAffordableDevices(float maxPrice) {
        List<SmartDevice> affordableDevices = new ArrayList<>();
        for (SmartDevice device : deviceDataBase) {
            if (device != null && device.getPrice() <= maxPrice) {
                affordableDevices.add(device);
            }
        }
        return affordableDevices;
    }

    /**
     * This method finds the index of the smart device device id
     * @param deviceID
     * @return indexOfSmartDevice
     */
    public int findDeviceIndexByID(Long deviceID) {
        for (int i = 0; i < this.deviceDataBase.length; i++) {
            SmartDevice device = deviceDataBase[i];
            if (device != null && device.getDeviceId() == deviceID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This function returns the smartDevice from the array by index
     * @param i - index
     * @return smartDevice
     */
    public SmartDevice getDeviceByIndex(int i) {
        return this.deviceDataBase[i];
    }
}
