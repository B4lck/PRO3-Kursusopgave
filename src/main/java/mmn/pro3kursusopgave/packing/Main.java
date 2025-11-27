package mmn.pro3kursusopgave.packing;

import mmn.pro3kursusopgave.cutting.CuttingCUI;
import mmn.pro3kursusopgave.cutting.CuttingGrpcClient;

public class Main {
    static PackingCUI cui = new PackingCUI(/* INDSÆT KLIENT HER */);

    public static void main(String[] args) {
        cui.start();
    }
}
