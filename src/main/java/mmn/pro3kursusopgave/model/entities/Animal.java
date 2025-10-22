package mmn.pro3kursusopgave.model.entities;

public class Animal {
    private final double weight;
    private final String animalType;
    private final int animalId;

    public Animal(double weight, String animalType, int animalId) {
        this.weight = weight;
        this.animalType = animalType;
        this.animalId = animalId;
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

    @Override
    public String toString() {
        return animalType + " - weight: " + weight + " - ID: " + animalId;
    }
}
