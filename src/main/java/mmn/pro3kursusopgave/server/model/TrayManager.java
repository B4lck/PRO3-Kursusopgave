package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.model.entities.Tray;

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
     * @param animalPartType Arten af dyret.
     * @return ID'et på trayet
     */
    int addTray(double max_weight, String animalPartType);

    /**
     * Sletter et tray
     * @param id ID'et på trayet
     */
    int deleteTray(int id);

    /**
     * Henter alle trays i en pakke
     * @param packageNumber ID'et på pakken
     */
    List<Tray> getAllTraysInPackage(int packageNumber);
}
