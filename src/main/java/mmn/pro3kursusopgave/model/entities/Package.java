package mmn.pro3kursusopgave.model.entities;

import java.time.LocalDate;

public class Package {
    private final int packageNumber;
    private final LocalDate expireDate;

    public Package(int packageNumber, LocalDate expireDate) {
        this.packageNumber = packageNumber;
        this.expireDate = expireDate;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    @Override
    public String toString() {
        return "ID: " + packageNumber + " - Expire date: " + expireDate.toString();
    }
}
