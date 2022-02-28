import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOfLifeTest {
    /**
     * Rules
     * 1) Game of Life board is a square with a size (e.g. 10x10)
     * 2) Cells within the board are randomly given a state (Dead or Alive) at the beginning
     * 3) Cells outside the board are given an unknown state
     * 4) The next state calculation of cell is defined by these rules :
     * * 4.1) A living cell with less than two neighbors alive => dies
     * * 4.2) A living cell with two or three neighbors alive => survives
     * * 4.3) A living cell with more than three neighbors alive => dies
     * * 4.4) A dead cell with exactly three neighbors alive => becomes alive
     * * 4.5) Unknown cell are ignored
     * 5) The previous rules are applied to all cells after each generation
     */

    @ParameterizedTest
    @CsvSource({
            "1,1, UNKNOWN, '[UNKNOWN]'",
            "2,2, DEAD, '[DEAD DEAD\n" +
                    "DEAD DEAD]'",
            "3,3, ALIVE, '[ALIVE ALIVE ALIVE\n" +
                    "ALIVE ALIVE ALIVE\n" +
                    "ALIVE ALIVE ALIVE]'"
    })
    void beFilledWithRandomCellStatusAtGameBeginning(
            int boardSizeX,
            int boardSizeY,
            CellState definedRandomResult,
            String expectedCellStates
    ) {
        // Arrange
        RandomCellStateGenerator stateGenerator = new PredictableRandomCellStateGenerator(definedRandomResult);
        ConsoleOutputGenerationBoard generationBoard = new ConsoleOutputGenerationBoard();

        // Act
        new World(boardSizeX, boardSizeY, stateGenerator, generationBoard);

        // Assert
        assertEquals(expectedCellStates, generationBoard.printed());
    }
}
