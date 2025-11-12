package mmn.pro3kursusopgave.server.model.entities;

public class Animal {
    private final double weight;
    private final String animalType;
    private final int animalId;
    private final String origin;
    private final long dateTime;

    public Animal(double weight, String animalType, int animalId, String origin, long dateTime) {
        this.weight = weight;
        this.animalType = animalType;
        this.animalId = animalId;
        this.origin = origin;
        this.dateTime = dateTime;
    }

    public double getWeight() {
        return weight;
    }

    public String getAnimalType() {
        return animalType;
    }

    public int getAnimalId() {
        return animalId;
    }

    public String getOrigin() {
        return origin;
    }

    public long getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return animalType + " - weight: " + weight + " - ID: " + animalId;
    }
}
