package org.example.model;

import org.example.model.entities.AnimalPart;

import java.util.List;

public interface AnimalPartManager {
    List<AnimalPart> getAllAnimalParts();
    AnimalPart getPart(int id);
    List<AnimalPart> getAllPartsFromParent(int id);
    int addAnimalPart(double weight, int tray, int fromAnimal, String description);
    int deleteAnimalPart(int id);
}
