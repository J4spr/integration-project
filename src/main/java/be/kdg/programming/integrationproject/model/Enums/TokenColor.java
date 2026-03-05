package be.kdg.programming.integrationproject.model.Enums;

public enum TokenColor {
    RED("red"),
    GREEN("green"),
    YELLOW("yellow"),
    BLUE("blue");

    private String color;
    private TokenColor(String color){
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
