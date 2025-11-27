package mmn.pro3kursusopgave.cutting;

public class Main {
    static CuttingCUI cui = new CuttingCUI(new CuttingGrpcClient());

    public static void main(String[] args) {
        cui.start();
    }
}
