package mmn.pro3kursusopgave.checkin;

import java.time.Instant;
import java.util.Scanner;

public class CheckinCUI {
    private Scanner input;
    private CheckinGrpcClient client;

    public CheckinCUI(CheckinGrpcClient client) {
        input = new Scanner(System.in);
        this.client = client;
    }

    public void start() {
        System.out.println("CHECK IN STATION");

        while (true) {
            System.out.println("\u001B[33mVÆLG:\u001B[0m");
            System.out.println("  1. REGISTRER DYR");
            System.out.println("     (registrer et dyr og gemmer det)");
            System.out.println("  2. GEM");
            System.out.println("     (brug denne funktion hvis serveren har været utilgængelig, til at forsøge at gemme registrerede dyr)");

            if (client.getQueueSize() > 0) System.out.println("\u001B[31mDER ER " + client.getQueueSize() + " DYR SOM IKKE ER BLEVET UPLOADET, PRØV IGEN MED 2. FUNKTION\u001B[0m");

            navi: while (true) {
                String choice = input.nextLine().trim();
                switch (choice.substring(0, 1)) {
                    case "1":
                        checkinAnimal();
                        break navi;
                    case "2":
                        retrySaving();
                        break navi;
                    default:
                        System.out.println("Ugyldigt valg: " + choice);
                        break;
                }
            }
        }
    }

    public void checkinAnimal() {
        System.out.println("Check in et nyt dyr:");
        System.out.print("  - dyrets vægt i kg: ");
        double weight = Double.parseDouble(input.nextLine());
        System.out.print("  - dyrets art: ");
        String type = input.nextLine();
        System.out.print("  - dyrets origin: ");
        String origin = input.nextLine();
        long date = Instant.now().toEpochMilli();

        if (client.registerAnimal(weight, type, origin, date)) {
            // Kald server
            System.out.println("Dyret blev registreret");
        } else {
            System.out.println("Serveren ser ud til at være offline.");
            System.out.println("Dyret er blevet registreret lokalt, og vil uploades senere når serveren er tilgængelig.");
        }
    }

    public void retrySaving() {
        if (!client.save()) {
            System.out.println("Serveren ser ud til at være offline.");
        }
    }
}
