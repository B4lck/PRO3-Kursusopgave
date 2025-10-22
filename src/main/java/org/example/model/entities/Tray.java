package org.example.model.entities;

public class Tray {
    private final double maxWeight;
    private final String typeOfAnimal;
    private final int packageNumber;
    private final int trayNumber;

    public Tray(double maxWeight, String typeOfAnimal, int packageNumber, int trayNumber) {
        this.maxWeight = maxWeight;
        this.typeOfAnimal = typeOfAnimal;
        this.packageNumber = packageNumber;
        this.trayNumber = trayNumber;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public int getTrayNumber() {
        return trayNumber;
    }

    @Override
    public String toString() {
        return typeOfAnimal + " - max weight: " + maxWeight + " - package ID: " + packageNumber + " - ID: " + trayNumber;
    }
}
