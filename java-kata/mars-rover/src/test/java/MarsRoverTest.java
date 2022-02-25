import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
     * the last possible point e.g. 0:2:N if obstacle at (0,3)
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
        assertEquals("0:0:" + finalDirection, new MarsRover().execute(commands.split("")));
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
        assertEquals("0:0:" + finalDirection, new MarsRover().execute(commands.split("")));
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, 0, 0, M, 0:1:N",
            "NORTH, 0, 0, MMM, 0:3:N",
            "EAST, 0, 0, M, 1:0:E",
            "EAST, 0, 0, MMM, 3:0:E",
            "SOUTH, 0, 5, M, 0:4:S",
            "SOUTH, 0, 5, MMM, 0:2:S",
            "WEST, 5, 0, M, 4:0:W",
            "WEST, 5, 0, MMM, 2:0:W",
    })
    void shouldBeAtFinalPositionAndDirectionWhenMovingCommands(Direction direction, int x, int y, String commands,
                                                               String finalPositionAndDirection) {
        assertEquals(finalPositionAndDirection,
                new MarsRover(direction, new Coordinates(x, y)).execute(commands.split("")));
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, 0, 9, M, 0:0:N",
            "EAST, 9, 0, M, 0:0:E",
            "SOUTH, 0, 0, M, 0:9:S",
            "WEST, 0, 0, M, 9:0:W",
    })
    void shouldWrapAroundTheMapAndBeAtFinalPositionAndDirectionWhenMovingCommands(Direction direction,
                                                                                  int x, int y,
                                                                                  String commands,
                                                                                  String finalPositionAndDirection) {
        assertEquals(finalPositionAndDirection,
                new MarsRover(direction, new Coordinates(x, y)).execute(commands.split("")));
    }

    @ParameterizedTest
    @CsvSource({
            "NORTH, 5, 2, MMMM, 5:4:N",
            "EAST, 2, 5, MMMM, 4:5:E",
            "SOUTH, 5, 8, MMMM, 5:6:S",
            "WEST, 8, 5, MMMM, 6:5:W",
    })
    void shouldStopBeforeTheObstacleInTheMiddleOfTheMapWhenMovingCommands(Direction direction,
                                                                          int x, int y,
                                                                          String commands,
                                                                          String finalPositionAndDirection) {
        Grid grid = Grid.withLimitAndObstacles(10, 10, List.of(new Coordinates(5, 5)));
        assertEquals(finalPositionAndDirection,
                new MarsRover(direction, new Coordinates(x, y), grid).execute(commands.split("")));
    }
}
