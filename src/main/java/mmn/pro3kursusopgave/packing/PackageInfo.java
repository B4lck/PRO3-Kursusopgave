package mmn.pro3kursusopgave.packing;

import java.util.List;

public class PackageInfo {
    private List<Integer> trays;
    private long expireDate;
    private boolean uploaded;

    public PackageInfo(List<Integer> trays, long expireDate) {
        this.trays = trays;
        this.expireDate = expireDate;
    }

    public List<Integer> getTrays() {
        return trays;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void addTray(int trayId) {
        trays.add(trayId);
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded() {
        this.uploaded = true;
    }
}
