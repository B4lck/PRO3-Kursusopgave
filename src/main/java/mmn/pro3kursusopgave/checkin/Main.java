package mmn.pro3kursusopgave.checkin;

public class Main {
    static CheckinCUI cui = new CheckinCUI(new CheckinGrpcClient());

    public static void main(String[] args) {
        cui.start();
    }
}
