package mmn.pro3kursusopgave.client.responses;

// Simpel DTO til REST (ingen protobuf-magiske felter)
public class AnimalResponse {
    public int animalNumber;
    public double weight;
    public String typeOfAnimal;
    public String origin;
    public long dateTime;

    public AnimalResponse(int animalNumber, double weight, String typeOfAnimal, String origin, long dateTime) {
        this.animalNumber = animalNumber;
        this.weight = weight;
        this.typeOfAnimal = typeOfAnimal;
        this.origin = origin;
    }
}
