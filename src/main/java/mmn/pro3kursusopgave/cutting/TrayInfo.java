package mmn.pro3kursusopgave.cutting;

import java.util.ArrayList;
import java.util.List;

public class TrayInfo {
    private String description;
    private double maxWeight;
    private double currentWeight;
    private int count;
    private List<PartInfo> parts = new ArrayList<>();

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

    public void addPart(PartInfo part) {
        double weight = part.getWeight();
        if (currentWeight + weight > maxWeight) throw new IllegalStateException("Maks vægt nået");

        count++;
        currentWeight += weight;
        parts.add(part);
    }
}
