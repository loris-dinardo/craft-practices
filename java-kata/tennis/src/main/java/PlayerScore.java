public class PlayerScore {
    private String score;

    public PlayerScore(String initialScore) {
        score = initialScore;
    }

    public static PlayerScore withoutInitialValue() {
        return new PlayerScore("0");
    }

    public static PlayerScore withInitialValue(String initialValue) {
        return new PlayerScore(initialValue);
    }

    public String getScore() {
        return score;
    }

    public void playedHasScored(){
        if (this.score.equals("15")) {
            this.score = "30";
        } else {
            this.score = "15";
        }
    }
}
