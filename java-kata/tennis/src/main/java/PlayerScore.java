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

    public void playedHasScored() {
        switch (this.score) {
            case "40":
                this.score = "Won";
                break;
            case "30":
                this.score = "40";
                break;
            case "15":
                this.score = "30";
                break;
            case "0":
                this.score = "15";
                break;
        }
    }
}
