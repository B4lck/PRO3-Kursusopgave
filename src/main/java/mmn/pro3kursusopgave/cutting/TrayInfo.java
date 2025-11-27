package mmn.pro3kursusopgave.cutting;

import java.util.ArrayList;
import java.util.List;

public class TrayInfo {
    private String description;
    private double maxWeight;
    private double currentWeight;
    private int count;
    private List<PartInfo> parts = new ArrayList<>();
    private boolean uploaded;
    private int uploadedId;

    public TrayInfo(String description, double maxWeight) {
        this.description = description;
        this.maxWeight = maxWeight;
    }

    public String getDescription() {
        return description;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public int getCount() {
        return count;
    }
    public List<PartInfo> getParts() {
        return parts;
    }

    public void addPart(PartInfo part) {
        double weight = part.getWeight();
        if (currentWeight + weight > maxWeight) throw new IllegalStateException("Maks vægt nået");

        count++;
        currentWeight += weight;
        parts.add(part);
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public int getUploadedId() {
        return uploadedId;
    }

    public void setUploaded(int id) {
        this.uploaded = true;
        this.uploadedId = id;
    }
}
