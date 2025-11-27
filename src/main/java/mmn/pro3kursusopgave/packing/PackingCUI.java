package mmn.pro3kursusopgave.packing;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Scanner;

public class PackingCUI {
    private final PackingGrpcClient client;
    Scanner input;

    PackageInfo currentPackage;

    public PackingCUI(PackingGrpcClient client) {
        input = new Scanner(System.in);
        this.client = client;
    }

    public void start() {
        System.out.println("PACKING STATION");

        while (true) {
            System.out.println("\u001B[33mNUVÆRENDE PACKAGE/PRODUCT:\u001B[0m");
            if (currentPackage != null) {
                System.out.println("  - ANTAL DELE: " + currentPackage.getCount());
                System.out.println("  - EXPIRY DATE: " + Instant.ofEpochMilli(currentPackage.getExpireDate()).atZone(ZoneId.systemDefault()));
            } else {
                System.out.println("  - \u001B[31mINGEN PACKAGE\u001B[0m, Brug valgmulighed 1 for at oprette en package/et produkt");
            }
            System.out.println("\u001B[33mVÆLG:\u001B[0m");
            System.out.println("  1. NY PACKAGE/PRODUKT");
            System.out.println("     (gemmer det nuværende produkt, og opretter et nyt)");
            System.out.println("  2. TILFØJ TRAY");
            System.out.println("     (tilføjer en tray til produktet)");
            System.out.println("  3. GEM");
            System.out.println("     (gemmer produktet, hvis netværket har været nede, vil dette også gemme alle lokalt-gemte produkter)");

//            if (client.getQueueSize() > 0) System.out.println("\u001B[31mDER ER " + client.getQueueSize() + " PRODUKT(ER) SOM IKKE ER BLEVET UPLOADET, PRØV IGEN MED 3. FUNKTION\u001B[0m");

            navi:
            while (true) {
                String choice = input.nextLine().trim();
                switch (choice.substring(0, 1)) {
                    case "1":
                        createPackage();
                        break navi;
                    case "2":
                        addTrayToPackage();
                        break navi;
                    case "3":
                        savePackage();
                        break navi;
                    default:
                        System.out.println("Ugyldigt valg: " + choice);
                        break;
                }
            }
        }
    }

    public void createPackage() {
        if (currentPackage != null) {
            savePackage();
        }

        System.out.println("Opretter en ny package/produkt");
        System.out.print("  - hvor mange dage indtil expiry: ");
        double days = Double.parseDouble(input.nextLine());
        currentPackage = new PackageInfo(Instant.now().toEpochMilli() + (long) (days * 24 * 60 * 60 * 1000));
    }

    public void addTrayToPackage() {
        if (currentPackage == null) {
            System.out.println("\u001B[31mNæ næ du! Ingen package!\u001B[0m");
            return;
        }

        System.out.println("Tilføj et tray til den nuværende package");
        System.out.print("  - ID på tray: ");
        int id = Integer.parseInt(input.nextLine());

        currentPackage.addTray(id);

        System.out.println("\u001B[32mTilføjede tray'et til package\u001B[0m");
    }

    public void savePackage() {
        System.out.println("Gemmer packages");

//        client.createPackage(currentPackage);

        currentPackage = null;

        System.out.println("\u001B[32mPackage gemt\u001B[0m");
    }
}
