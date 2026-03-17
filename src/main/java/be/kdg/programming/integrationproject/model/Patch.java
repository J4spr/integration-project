package be.kdg.programming.integrationproject.model;

public class Patch {
    private int patchId;
    private int buttonCost;
    private int timeCost;
    private int buttonIncome;

    public Patch() {}

    public Patch(int patchId, int buttonCost, int timeCost, int buttonIncome) {
        this.patchId = patchId;
        this.buttonCost = buttonCost;
        this.timeCost = timeCost;
        this.buttonIncome = buttonIncome;
    }

    public int getPatchId() { return patchId; }
    public void setPatchId(int patchId) { this.patchId = patchId; }

    public int getButtonCost() { return buttonCost; }
    public void setButtonCost(int buttonCost) { this.buttonCost = buttonCost; }

    public int getTimeCost() { return timeCost; }
    public void setTimeCost(int timeCost) { this.timeCost = timeCost; }

    public int getButtonIncome() { return buttonIncome; }
    public void setButtonIncome(int buttonIncome) { this.buttonIncome = buttonIncome; }
}