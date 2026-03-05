package be.kdg.programming.integrationproject.model.Enums;

public enum GameStatus {
    ACTIVE("active"),
    FINISHED("finished"),
    PAUSED("paused");

    private String status;
    private GameStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
