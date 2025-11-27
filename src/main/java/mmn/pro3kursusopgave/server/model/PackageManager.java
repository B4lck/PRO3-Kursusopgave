package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.model.entities.Package;

import java.time.LocalDate;
import java.util.List;

/**
 * Håndtere alt om produkterne fra slagteriet.
 * Mere specifikt `package` tabellen i databasen.
 */
public interface PackageManager {
    /**
     * Henter alle produkter.
     */
    List<Package> getAllPackages();

    /**
     * Henter et enkelt produkt.
     * @param id ID'et på produktet.
     */
    Package getPackage(int id);

    /**
     * Tilføjet et produkt.
     * TODO: Skal tage en liste af tray id'er med hvor kødet er fra.
     * @param expireDate Udløbsdatoen af produktet.
     */
    int addPackage(long expireDate);

    /**
     * Sletter et produkt
     * @param id ID'et på produktet.
     */
    int deletePackage(int id);

    /**
     * Indsætter tray i en pakke
     * @param trayId ID'et på tray
     * @param packageId ID'et på package
     * @throws RuntimeException Hvis package / tray ikke findes
     */
    void addTrayToPackage(int trayId, int packageId);
}
