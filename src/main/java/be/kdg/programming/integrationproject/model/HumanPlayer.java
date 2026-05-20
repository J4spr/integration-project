package be.kdg.programming.integrationproject.model;

/**
 * Concrete participant instance representing an active human user interaction model profile.
 *
 * @author Team 4
 * @version 1.0
 */
public class HumanPlayer extends Player {
    /** The profile identification text handle tracking user reference data logs. */
    private String name;

    /**
     * Instantiates a new HumanPlayer profile configuration initialized with core entity assets.
     *
     * @param name text identifier label string
     */
    public HumanPlayer(String name) {
        super();
        this.name = name;
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