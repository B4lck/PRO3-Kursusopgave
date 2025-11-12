package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.model.entities.Animal;

import java.util.List;

/**
 * Håndtere alt om dyrene registeret i slagteriet.
 * Mere specifikt `animal` tabellen i databasen.
 */
public interface AnimalManager {
    /**
     * Henter alle dyr i databasen i en liste.
     */
    List<Animal> getAllAnimals();

    /**
     * Henter et dyr.
     * @param id ID'et på dyret.
     */
    Animal getAnimal(int id);

    /**
     * Tilføjer et dyr.
     * @param weight Vægten på dyret.
     * @param type Arten af dyret.
     */
    int addAnimal(double weight, String type);

    /**
     * Sletter et dyr.
     * @param id ID'et på dyret.
     */
    int deleteAnimal(int id);

    /**
     * Henter alle dyr fra et bestemt produkt.
     * @param packageId ID'et på produktet.
     */
    List<Animal> getAllAnimalsInProduct(int packageId);
}
