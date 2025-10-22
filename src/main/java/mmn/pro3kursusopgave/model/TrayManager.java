package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.model.entities.Tray;

import java.util.List;

/**
 * Håndtere alt om trays i slagteriet.
 * Mere specifikt `tray` tabellen i databasen.
 */
public interface TrayManager {
    /**
     * Henter alle trays.
     */
    List<Tray> getAllTrays();

    /**
     * Henter et specifikt tray.
     * @param id ID'et på tray.
     */
    Tray getTray(int id);

    /**
     * Tilføjer et tray.
     * @param max_weight Vægten af tray.
     * @param animalType Arten af dyret. TODO: Skal være en animal part type
     * @param packageNumber Nummeret på den package som trayet bliver en del af. TODO: Skal være på PackageManager.addPackage
     * @return ID'et på trayet
     */
    int addTray(double max_weight, String animalType, int packageNumber);

    /**
     * Sletter et tray
     * @param id ID'et på trayet
     */
    int deleteTray(int id);
}
