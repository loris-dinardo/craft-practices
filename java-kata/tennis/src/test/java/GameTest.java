import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    @Description("When none of the players have scored, the score should be at its initial state")
    public void scoreShouldBeAtItsInitialStateWhenNoneOfThePlayersHaveScored() {
        assertEquals("0:0", GameSutBuilder.builder()
                .withEmptyScore()
                .build()
                .getScore());
    }

    @Test
    @Description("When game has just started and player One scored, the score should indicate that player One has " +
            "scored once")
    public void scoreShouldBeAtFavorOfPlayerOneWhenHeScoredOnce() {
        assertEquals("15:0", GameSutBuilder.builder()
                .withEmptyScore()
                .withPlayerOneHasScored()
                .build()
                .getScore());
    }

    @Test
    @Description("When game has just started and player Two scored, the score should indicate that player Two has " +
            "scored once")
    public void scoreShouldBeAtFavorOfPlayerTwoWhenHeScoredOnce() {
        assertEquals("0:15", GameSutBuilder.builder()
                .withEmptyScore()
                .withPlayerTwoHasScored()
                .build()
                .getScore());
    }

    @Test
    @Description("When player One has already scored once, he scores again and player Two has not scored yet, the " +
            "score should indicate it")
    public void scoreShouldBeIndicatePlayerOneHasScoreTwiceWhenHeScoreASecondTime() {
        assertEquals("30:0", GameSutBuilder.builder()
                .withScore("15", "0")
                .withPlayerOneHasScored()
                .build()
                .getScore());
    }

    @Test
    @Description("When player Two has already scored once, he scores again and player One has not scored yet, the " +
            "score should indicate it")
    public void scoreShouldBeIndicatePlayerTwoHasScoreTwiceWhenHeScoreASecondTime() {
        assertEquals("0:30", GameSutBuilder.builder()
                .withScore("0", "15")
                .withPlayerTwoHasScored()
                .build()
                .getScore());
    }

    @Test
    @Description("When player One has already scored twice, he scores again and player Two has not scored yet, the " +
            "score should indicate it")
    public void scoreShouldBeIndicatePlayerOneHasScoreThreeTimeWhenHeScoreAThirdTime() {
        assertEquals("40:0", GameSutBuilder.builder()
                .withScore("30", "0")
                .withPlayerOneHasScored()
                .build()
                .getScore());
    }

    @Test
    @Description("When player One has already scored three time, he scores again and player Two has not scored yet, " +
            "the game should say he won the game")
    public void gameShouldIndicateThatPlayerOneHasWonWhenHeHasAlreadyScoredThreeTimeAndHeScoreAFourthTime() {
        assertEquals("Player One", GameSutBuilder.builder()
                .withScore("40", "0")
                .withPlayerOneHasScored()
                .build()
                .getWinner());
    }
}
