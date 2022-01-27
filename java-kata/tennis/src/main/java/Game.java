public class Game {
    private final PlayerScore scorePlayerOne;
    private final PlayerScore scorePlayerTwo;

    public Game() {
        this.scorePlayerOne = PlayerScore.withoutInitialValue();
        this.scorePlayerTwo = PlayerScore.withoutInitialValue();
    }

    public Game(String playerOneInitialScore, String playerTwoInitialScore) {
        this.scorePlayerOne = PlayerScore.withInitialValue(playerOneInitialScore);
        this.scorePlayerTwo = PlayerScore.withInitialValue(playerTwoInitialScore);
    }

    public String getScore() {
        return this.scorePlayerOne.getScore() + ":" + this.scorePlayerTwo.getScore();
    }

    public void playerOneHasScored() {
        this.scorePlayerOne.playedHasScored();
    }

    public void playerTwoHasScored() {
        this.scorePlayerTwo.playedHasScored();
    }
}
