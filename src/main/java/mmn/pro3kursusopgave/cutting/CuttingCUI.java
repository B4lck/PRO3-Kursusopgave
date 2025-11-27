package mmn.pro3kursusopgave.cutting;

import java.time.Instant;
import java.util.Scanner;

public class CuttingCUI {
    private final CuttingGrpcClient client;
    Scanner input;

    TrayInfo currentTray;

    public CuttingCUI(CuttingGrpcClient client) {
        input = new Scanner(System.in);

        this.client = client;
    }

    public void start() {
        System.out.println("CUTTING STATION");

        while (true) {
            System.out.println("\u001B[33mNUVÆRENDE TRAY:\u001B[0m");
            if (currentTray != null) {
                System.out.println("  - KØD BESKRIVELSE: " + currentTray.getTypeOfPart());
                System.out.println("  - MAKS. VÆGT: " + currentTray.getMaxWeight());
                System.out.println("  - VÆGT: " + currentTray.getCurrentWeight());
                System.out.println("  - Antal dele: " + currentTray.getCount());
            } else {
                System.out.println("  - \u001B[31mINGEN TRAY\u001B[0m, Brug valgmulighed 1 for at oprette et tray");
            }
            System.out.println("\u001B[33mVÆLG:\u001B[0m");
            System.out.println("  1. NY TRAY");
            System.out.println("     (gemmer det nuværende tray, og opretter et nyt tray)");
            System.out.println("  2. NY SKÆRING");
            System.out.println("     (opretter en ny skæring, som bliver tilknyttet det nuværende tray)");
            System.out.println("  3. GEM");
            System.out.println("     (gemmer det nuværende tray + skæringer, hvis netværket har været nede, vil dette også gemme alle lokalt-gemte trays)");

            if (client.getQueueSize() > 0) System.out.println("\u001B[31mDER ER " + client.getQueueSize() + " TRAY(S) SOM IKKE ER BLEVET UPLOADET, PRØV IGEN MED 3. FUNKTION\u001B[0m");

            navi:
            while (true) {
                String choice = input.nextLine().trim();
                switch (choice.substring(0, 1)) {
                    case "1":
                        createTray();
                        break navi;
                    case "2":
                        addPartToTray();
                        break navi;
                    case "3":
                        saveTray();
                        break navi;
                    default:
                        System.out.println("Ugyldigt valg: " + choice);
                        break;
                }
            }
        }
    }

    public void createTray() {
        if (currentTray != null) {
            saveTray();
        }

        System.out.println("Opretter nyt tray");
        System.out.print("  - beskrivelse af kød: ");
        String description = input.nextLine();
        System.out.print("  - maks vægt: ");
        double maxWeight = Double.parseDouble(input.nextLine());
        currentTray = new TrayInfo(description, maxWeight);
    }

    public void addPartToTray() {
        if (currentTray == null) {
            System.out.println("\u001B[31mNæ næ du! Ingen tray!\u001B[0m");
            return;
        }

        System.out.println("Opretter ny skæring");
        System.out.println("  - beskrivelse af kød: " + currentTray.getTypeOfPart());
        System.out.print("  - ID på dyr: ");
        int id = Integer.parseInt(input.nextLine());
        System.out.print("  - vægt (maks. " + (currentTray.getMaxWeight() - currentTray.getCurrentWeight()) + "): ");
        double weight = Double.parseDouble(input.nextLine());
        long date = Instant.now().toEpochMilli();

        PartInfo part = new PartInfo(weight, id, currentTray.getTypeOfPart(), date);

        currentTray.addPart(part);


        System.out.println("\u001B[32mTilføjede skæring\u001B[0m");
    }

    public void saveTray() {
        System.out.println("Gemmer trays");

        client.createTray(currentTray);

        currentTray = null;

        System.out.println("\u001B[32mTrays gemt\u001B[0m");
    }
}
