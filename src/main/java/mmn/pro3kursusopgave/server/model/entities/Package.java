package mmn.pro3kursusopgave.server.model.entities;

import java.util.List;

public class Package {
    private final int packageNumber;
    private final long expireDate;
    private final List<Tray> trays;

    public Package(int packageNumber, long expireDate, List<Tray> trays) {
        this.packageNumber = packageNumber;
        this.expireDate = expireDate;
        this.trays = trays;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public List<Tray> getTrays() {
        return trays;
    }

    @Override
    public String toString() {
        return "ID: " + packageNumber + " - Expire date: " + expireDate;
    }
}
