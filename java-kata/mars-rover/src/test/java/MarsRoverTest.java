import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarsRoverTest {
    /**
     * Mars Rover Kata Rules
     * <p>
     * 1) You are given the initial starting point (0,0,N) of a rover.
     * 2) 0,0 are X,Y coordinates on a grid of (10,10).
     * 3) N is the direction it is facing (i.e. N,S,E,W).
     * 4) L and R allow the rover to rotate left and right.
     * 5) M allows the rover to move one point in the current direction.
     * 6) The rover receives a String array of commands e.g. RMMLM and returns the finishing point after the moves e.g
     * 2:1:N.
     * 7) The rover wraps around if it reaches the end of the grid.
     * 8) The grid may have obstacles. If a given sequence of commands encounters an obstacle, the rover moves up to
     * the last possible point and reports the obstacle e.g. 0:2:2:N
     */

    @ParameterizedTest
    @CsvSource({
            "'', N",
            "R, E",
            "RR, S",
            "RRR, W",
            "RRRR, N"
    })
    void shouldFaceFinalDirectionWhenRotateRightCommands(String commands, String finalDirection) {
        assertEquals("0:0:" + finalDirection, new MarsRover("N").execute(commands.split("")));
    }

    @ParameterizedTest
    @CsvSource({
            "'', N",
            "L, W",
            "LL, S",
            "LLL, E",
            "LLLL, N"
    })
    void shouldFaceFinalDirectionWhenRotateLeftCommands(String commands, String finalDirection) {
        assertEquals("0:0:" + finalDirection, new MarsRover("N").execute(commands.split("")));
    }
}