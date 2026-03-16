package be.kdg.programming.integrationproject.model;

public class HumanPlayer extends Player{
    private String name;

    public HumanPlayer(){

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }
}