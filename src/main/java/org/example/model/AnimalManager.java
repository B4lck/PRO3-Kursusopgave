package org.example.model;

import org.example.model.entities.Animal;

import java.util.List;

public interface AnimalManager {
    List<Animal> getAllAnimals();
    Animal getAnimal(int id);
    int addAnimal(double weight, String type);
    void deleteAnimal(int id);
}
