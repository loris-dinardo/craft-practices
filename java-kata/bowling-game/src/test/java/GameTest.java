import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game sut;

    @BeforeEach
    void setUp() {
        sut = new Game();
    }

    @Test
    void shouldScoreZeroWhenGutterGame() {
        sut.roll(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertEquals(0, sut.score());
    }

    @Test
    void shouldScoreTwentyWhenGameOfOnes() {
        sut.roll(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        assertEquals(20, sut.score());
    }

    @Test
    void shouldScore16WhenSpareFollowedBy3_AndFollowedByZeros() {
        sut.roll(5, 5, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertEquals(16, sut.score());
    }

    @Test
    void shouldScore22WhenStrikeFollowedBy3And3_AndFollowedByZeros(){
        sut.roll(10, 3,3, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0);
        assertEquals(22, sut.score());
    }

    @Test
    void shouldScore300WhenPerfectGame(){
        sut.roll(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(300, sut.score());
    }

    @Test
    void shouldScore143WhenRandomGame(){
        sut.roll(4,6, 8,0, 10, 3,5, 9,1, 10, 4,2, 9,0, 5,5, 10, 9,1);
        assertEquals(143, sut.score());
    }
}
