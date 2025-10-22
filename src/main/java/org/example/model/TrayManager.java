package org.example.model;

import org.example.model.entities.Tray;

import java.util.List;

public interface TrayManager {
    List<Tray> getAllTrays();
    Tray getTrays(int id);
    void addTray(double max_weight, String animalType, int packageNumber);
    void deleteTray(int id);
}
