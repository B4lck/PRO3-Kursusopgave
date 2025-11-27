package mmn.pro3kursusopgave.packing;

public class Main {
    static PackingCUI cui = new PackingCUI(new PackingGrpcClient());

    public static void main(String[] args) {
        cui.start();
    }
}
