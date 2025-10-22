package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.model.entities.Animal;

import java.util.List;

public interface AnimalManager {
    List<Animal> getAllAnimals();
    Animal getAnimal(int id);
    int addAnimal(double weight, String type);
    int deleteAnimal(int id);
    List<Animal> getAllAnimalsInProduct(int packageId);
}
