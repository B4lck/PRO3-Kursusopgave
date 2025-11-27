package mmn.pro3kursusopgave.server.model.entities;

public class Tray {
    private final double maxWeight;
    private final String typeOfPart;
    private final int trayNumber;

    public Tray(double maxWeight, String typeOfAnimal, int trayNumber) {
        this.maxWeight = maxWeight;
        this.typeOfPart = typeOfAnimal;
        this.trayNumber = trayNumber;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public String getTypeOfPart() {
        return typeOfPart;
    }

    public int getTrayNumber() {
        return trayNumber;
    }

    @Override
    public String toString() {
        return typeOfPart + " - max weight: " + maxWeight + " - ID: " + trayNumber;
    }
}
