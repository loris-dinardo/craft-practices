import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOfLifeTest {
    /**
     * Rules
     * 1) Game of Life board is a square with a size (e.g. 10x10)
     * 2) Cells within the board are randomly given a state (Dead or Alive) at the beginning
     * 3) The next state calculation of cell is defined by these rules :
     * * 3.1) A living cell with less than two neighbors alive => dies
     * * 3.2) A living cell with two or three neighbors alive => survives
     * * 3.3) A living cell with more than three neighbors alive => dies
     * * 3.4) A dead cell with exactly three neighbors alive => becomes alive
     * * 3.5) Unknown cell are ignored
     * 4) The previous rules are applied to all cells after each generation
     */

    @Nested
    class CellShouldTest {
        @ParameterizedTest
        @CsvSource({
                "ALIVE, 0, DEAD",
                "ALIVE, 1, DEAD",
                "ALIVE, 2, ALIVE",
                "ALIVE, 3, ALIVE",
                "ALIVE, 4, DEAD",
                "ALIVE, 8, DEAD",
                "DEAD, 0, DEAD",
                "DEAD, 1, DEAD",
                "DEAD, 2, DEAD",
                "DEAD, 3, ALIVE",
                "DEAD, 4, DEAD",
                "DEAD, 8, DEAD",
        })
        void haveExpectedStateOnNextGenerationWhenSpecificNumberOfNeighbors(
                CellState initialCellState,
                int numberOfNeighborsAlive,
                CellState expectedCellState
        ) {
            // Arrange
            Cell sut = new Cell(initialCellState);

            // Act
            Cell nextGenerationCell = sut.nextGenerationStateWhenNumberOfNeighborsAliveIs(numberOfNeighborsAlive);

            // Assert
            assertEquals(expectedCellState, nextGenerationCell.getState());
        }
    }

    @Nested
    class WorldBoardShouldTest {
        @ParameterizedTest
        @CsvSource({
                "1,1, ALIVE, '[ALIVE]'",
                "2,2, DEAD#ALIVE, '[DEAD ALIVE\n" +
                        "DEAD ALIVE]'",
                "3,3, ALIVE#ALIVE#DEAD#DEAD, '[ALIVE ALIVE DEAD\n" +
                        "DEAD ALIVE ALIVE\n" +
                        "DEAD DEAD ALIVE]'"
        })
        void beFilledWithRandomCellStatusAtGameBeginning(
                int boardSizeX,
                int boardSizeY,
                String definedRandomResultChain,
                String expectedCellStates
        ) {
            // Arrange
            RandomCellStateGenerator stateGenerator = new PredictableRandomCellStateGenerator(definedRandomResultChain);
            ConsoleOutputGenerationBoard outputGenerationBoard = new ConsoleOutputGenerationBoard();

            // Act
            new World(boardSizeX, boardSizeY, stateGenerator, outputGenerationBoard);

            // Assert
            assertEquals(expectedCellStates, outputGenerationBoard.printed());
        }
    }
}
