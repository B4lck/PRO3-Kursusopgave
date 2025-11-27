package mmn.pro3kursusopgave.server.model.entities;

public class AnimalPart {
    private final double weight;
    private final int trayNumber;
    private final int fromAnimalId;
    private final int animalPartId;
    private final String typeOfPart;
    private final long cuttingDate;

    public AnimalPart(double weight, int trayNumber, int fromAnimalId, int animalPartId, String description, long cuttingDate) {
        this.weight = weight;
        this.trayNumber = trayNumber;
        this.fromAnimalId = fromAnimalId;
        this.animalPartId = animalPartId;
        this.typeOfPart = description;
        this.cuttingDate = cuttingDate;
    }

    public double getWeight() {
        return weight;
    }

    public int getTrayNumber() {
        return trayNumber;
    }

    public int getFromAnimalId() {
        return fromAnimalId;
    }

    public int getAnimalPartId() {
        return animalPartId;
    }

    public String getTypeOfPart() {
        return typeOfPart;
    }

    public long getCuttingDate() {
        return cuttingDate;
    }

    @Override
    public String toString() {
        return typeOfPart + " - weight: " + weight + " - tray: " + trayNumber + " - from animal: " + fromAnimalId + " - ID: " + animalPartId;
    }
}
