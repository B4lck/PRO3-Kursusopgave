package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.model.entities.AnimalPart;

import java.util.List;

/**
 * Håndtere alt om delene af dyr registeret i slagteriet.
 * Mere specifikt `animalpart` tabellen i databasen.
 */
public interface AnimalPartManager {
    /**
     * Henter en liste med alle dele af dyr fra databasen.
     */
    List<AnimalPart> getAllAnimalParts();

    /**
     * Henter en dyr del efter ID.
     * @param id ID'et på delen.
     */
    AnimalPart getPart(int id);

    /**
     * Henter alle dele fra et bestemt dyr.
     * @param id ID'et på dyret.
     */
    List<AnimalPart> getAllPartsFromParent(int id);

    /**
     * Tilføjer en dyr del.
     * @param weight Vægten af delen.
     * @param tray ID'et på det tray delen skal i.
     * @param fromAnimal ID'et på det dyr delen er fra.
     * @param description Beskrivelse af delen. TODO: Skal nok fjernes, og blive til en part type?
     */
    int addAnimalPart(double weight, int tray, int fromAnimal, String description);

    /**
     * Sletter en animal del.
     * @param id ID'et på delen.
     */
    int deleteAnimalPart(int id);
}
