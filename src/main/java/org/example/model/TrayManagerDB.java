package org.example.model;

import org.example.model.entities.Tray;

import java.util.List;

public class TrayManagerDB implements TrayManager{
    @Override
    public List<Tray> getAllTrays() {
        return List.of();
    }

    @Override
    public Tray getTrays(int id) {
        return null;
    }

    @Override
    public void addTray(double max_weight, String animalType, int packageNumber) {

    }

    @Override
    public void deleteTray(int id) {

    }
}
