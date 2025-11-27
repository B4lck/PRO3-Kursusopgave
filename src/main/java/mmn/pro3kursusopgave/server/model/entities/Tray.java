package mmn.pro3kursusopgave.server.model.entities;

public class Tray {
    private final double maxWeight;
    private final String typeOfAnimal;
    private final int trayNumber;

    public Tray(double maxWeight, String typeOfAnimal, int trayNumber) {
        this.maxWeight = maxWeight;
        this.typeOfAnimal = typeOfAnimal;
        this.trayNumber = trayNumber;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }

    public int getTrayNumber() {
        return trayNumber;
    }

    @Override
    public String toString() {
        return typeOfAnimal + " - max weight: " + maxWeight + " - ID: " + trayNumber;
    }
}
