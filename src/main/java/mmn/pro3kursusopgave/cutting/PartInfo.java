package mmn.pro3kursusopgave.cutting;

public class PartInfo {
    private double weight;
    private int fromAnimal;
    private String typeOfPart;
    private long cuttingDate;

    private boolean uploaded;

    public PartInfo(double weight, int fromAnimal, String typeOfPart, long cuttingDate) {
        this.weight = weight;
        this.fromAnimal = fromAnimal;
        this.typeOfPart = typeOfPart;
        this.cuttingDate = cuttingDate;
    }

    public double getWeight() {
        return weight;
    }

    public int getFromAnimal() {
        return fromAnimal;
    }

    public String getTypeOfPart() {
        return typeOfPart;
    }

    public long getCuttingDate() {
        return cuttingDate;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded() {
        this.uploaded = true;
    }
}
