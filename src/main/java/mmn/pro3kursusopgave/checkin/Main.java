package mmn.pro3kursusopgave.checkin;

public class Main {
    static CUI cui = new CUI(new CheckinGrpcClient());

    public static void main(String[] args) {
        cui.start();
    }
}
