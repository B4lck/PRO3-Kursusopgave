package mmn.pro3kursusopgave.model.entities;

public class AnimalPart {
    private final double weight;
    private final int trayNumber;
    private final int fromAnimalId;
    private final int animalPartId;
    private final String description;

    public AnimalPart(double weight, int trayNumber, int fromAnimalId, int animalPartId, String description) {
        this.weight = weight;
        this.trayNumber = trayNumber;
        this.fromAnimalId = fromAnimalId;
        this.animalPartId = animalPartId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description + " - weight: " + weight + " - tray: " + trayNumber + " - from animal: " + fromAnimalId + " - ID: " + animalPartId;
    }
}
