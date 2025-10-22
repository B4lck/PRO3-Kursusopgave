package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.model.entities.Tray;

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
    public int addTray(double max_weight, String animalType, int packageNumber) {
        return 0;
    }

    @Override
    public int deleteTray(int id) {
        return 0;
    }
}
