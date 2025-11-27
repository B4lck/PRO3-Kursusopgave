package mmn.pro3kursusopgave.server.model.entities;

import java.time.LocalDate;
import java.util.List;

public class Package {
    private final int packageNumber;
    private final LocalDate expireDate;
    private final List<Tray> trays;

    public Package(int packageNumber, LocalDate expireDate, List<Tray> trays) {
        this.packageNumber = packageNumber;
        this.expireDate = expireDate;
        this.trays = trays;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public List<Tray> getTrays() {
        return trays;
    }

    @Override
    public String toString() {
        return "ID: " + packageNumber + " - Expire date: " + expireDate.toString();
    }
}
