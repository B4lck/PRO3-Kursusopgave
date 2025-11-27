package mmn.pro3kursusopgave.packing;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {
    private List<Integer> trays = new ArrayList<>();
    private long expireDate;
    private boolean uploaded;

    public PackageInfo(long expireDate) {
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

    public int getCount() {
        return trays.size();
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded() {
        this.uploaded = true;
    }
}
