package org.example;

import org.example.model.AnimalManager;
import org.example.model.AnimalManagerDB;

public class Main {
    public static void main(String[] args) {
        AnimalManager animalManager = new AnimalManagerDB();

        System.out.println(animalManager.getAllAnimals().size());
    }
}