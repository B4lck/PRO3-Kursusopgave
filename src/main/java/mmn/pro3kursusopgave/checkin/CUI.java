package mmn.pro3kursusopgave.checkin;

import java.time.Instant;
import java.util.Scanner;

public class CUI {
    private Scanner input;
    private CheckinGrpcClient client;

    public CUI(CheckinGrpcClient client) {
        input = new Scanner(System.in);
        this.client = client;
    }

    public void start() {
        System.out.println("CHECK IN STATION");

        while (true) {
            checkinAnimal();
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
            System.out.println("Der skete en fejl");
            System.out.println("Server er muligvis offline, prøver automatisk igen senere...");
            System.out.println("Dyret er blevet registreret lokalt.");
        }
    }
}
