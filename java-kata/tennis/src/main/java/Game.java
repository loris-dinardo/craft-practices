public class Game {
    private String scorePlayerOne;
    private String scorePlayerTwo;

    public Game() {
        this.scorePlayerOne = "0";
        this.scorePlayerTwo = "0";
    }

    public String getScore() {
        return this.scorePlayerOne + ":" + this.scorePlayerTwo;
    }

    public void playerOneHasScored() {
        this.scorePlayerOne = "15";
    }

    public void playerTwoHasScored() {
        this.scorePlayerTwo = "15";
    }
}
