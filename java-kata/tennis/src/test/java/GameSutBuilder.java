public class GameSutBuilder {
    private Game sut;

    public static GameSutBuilder builder() {
        return new GameSutBuilder();
    }

    public GameSutBuilder withEmptyScore() {
        sut = new Game();
        return this;
    }

    public GameSutBuilder withScore(String playerOneInitialScore,
                                    String playerTwoInitialScore) {
        sut = new Game(playerOneInitialScore, playerTwoInitialScore);
        return this;
    }

    public GameSutBuilder withPlayerOneHasScored() {
        sut.playerOneHasScored();
        return this;
    }

    public GameSutBuilder withPlayerTwoHasScored() {
        sut.playerTwoHasScored();
        return this;
    }

    public Game build() {
        return sut;
    }
}
