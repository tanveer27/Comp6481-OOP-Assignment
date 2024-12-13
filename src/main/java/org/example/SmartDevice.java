package org.example;

import java.util.Objects;

/**
 * Assignment: #0 (part 2A)
 * © Tanveer Reza
 * Written by: Tanveer Reza, Student ID: 40292971
 * This is the SmartDevice Object with all getters and setters, constructors and override methods
 */
public class SmartDevice {
    private static int numberOfSmartDevices = 0; // keeps record of the total number of smart devices
    private final long deviceId;
    private String deviceName;
    private String deviceType;
    private String osVersion;
    private float batteryLife;
    private float price;
    private boolean isInStock;

    /**
     * This is the default constructor for smartDevices
     */
    public SmartDevice() {
        this("Device 1", "Laptop", "Ubuntu 20", 5, 1000, true);
    }

    /**
     * This is the custom constructor for smart devices
     * @param deviceName
     * @param deviceType
     * @param osVersion
     * @param batteryLife
     * @param price
     * @param isInStock
     */
    public SmartDevice(String deviceName, String deviceType, String osVersion, float batteryLife, float price, boolean isInStock) {
        numberOfSmartDevices++; // increase the device count
        this.deviceId = numberOfSmartDevices;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.osVersion = osVersion;
        this.batteryLife = batteryLife;
        this.price = price;
        this.isInStock = isInStock;
    }

    public static int getNumberOfSmartDevices() {
        return numberOfSmartDevices;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public float getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(float batteryLife) {
        this.batteryLife = batteryLife;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    /**
     * Overriding toString method to for SmartDevice
     * @return string
     */
    @Override
    public String toString() {
        return "ID = " + deviceId + "\n" +
                "Device Name = " + deviceName + "\n" +
                "Device Type = " + deviceType + "\n" +
                "OS Version = " + osVersion + "\n" +
                "Battery Life = " + batteryLife + "\n" +
                "Price = " + price + "\n" +
                "Availability = " + isInStock +"\n";
    }

    /**
     * Overriding equals method for smartDevice
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return deviceId == that.deviceId && isInStock == that.isInStock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, isInStock);
    }
}
