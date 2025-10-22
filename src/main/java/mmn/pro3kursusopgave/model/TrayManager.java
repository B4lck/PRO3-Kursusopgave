package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.model.entities.Tray;

import java.util.List;

public interface TrayManager {
    List<Tray> getAllTrays();
    Tray getTrays(int id);
    int addTray(double max_weight, String animalType, int packageNumber);
    int deleteTray(int id);
}
