import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    @Description("When none of the players have scored, the score should be at its initial state")
    public void scoreShouldBeAtItsInitialStateWhenNoneOfThePlayersHaveScored() {
        //arrange
        Game sut = new Game();

        // act
        // assert
        assertEquals("0:0", sut.getScore());
    }

    @Test
    @Description("When game has just started and player One scored, the score should indicate that player One has scored once")
    public void scoreShouldBeAtFavorOfPlayerOneWhenHeScoredOnce(){
        // arrange
        Game sut = new Game();

        // act
        sut.playerOneHasScored();

        // assert
        assertEquals("15:0", sut.getScore());
    }

    @Test
    @Description("When game has started and player Two scored, the score should indicate that player Two has scored once")
    public void scoreShouldBeAtFavorOfPlayerTwoWhenHeScoredOnce(){
        // arrange
        Game sut = new Game();

        // act
        sut.playerTwoHasScored();

        // assert
        assertEquals("0:15", sut.getScore());
    }



}
