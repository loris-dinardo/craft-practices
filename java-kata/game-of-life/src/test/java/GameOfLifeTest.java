import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * 4) The previous rules are applied to all cells after each generation
     */

    @Nested
    class CellShouldTest {
        @ParameterizedTest
        @CsvSource({
                // Min number of neighbors is 3 when cell is in one of the corners
                "DEAD#DEAD#DEAD, ALIVE, DEAD",                                   // Rule 3.1
                "ALIVE#DEAD#DEAD, ALIVE, DEAD",                                  // Rule 3.1
                "ALIVE#ALIVE#DEAD, ALIVE, ALIVE",                                // Rule 3.1
                "ALIVE#ALIVE#ALIVE, ALIVE, ALIVE",                               // Rule 3.2
                "ALIVE#ALIVE#ALIVE#ALIVE, ALIVE, DEAD",                          // Rule 3.3
                // Max number of neighbors is 8
                "ALIVE#ALIVE#ALIVE#ALIVE#ALIVE#ALIVE#ALIVE#ALIVE, ALIVE, DEAD",  // Rule 3.3
                "DEAD#DEAD#DEAD, DEAD, DEAD",                                    // Rule 3.4
                "ALIVE#DEAD#DEAD, DEAD, DEAD",                                   // Rule 3.4
                "ALIVE#ALIVE#DEAD, DEAD, DEAD",                                  // Rule 3.4
                "ALIVE#ALIVE#ALIVE, DEAD, ALIVE",                                // Rule 3.4
        })
        void beAbleToDefineTheNextGenerationStateAccordingToTheNumberOfAliveNeighbors(
                String neighborsCellStates,
                CellState initialCellState,
                CellState expectedCellState
        ) {
            // Arrange
            List<Cell> neighbors = Arrays.stream(neighborsCellStates.split("#"))
                    .map(strState -> new Cell(CellState.valueOf(strState))).collect(Collectors.toList());
            Cell sut = new Cell(initialCellState, neighbors);

            // Act
            // Assert
            assertEquals(expectedCellState, sut.nextGenerationCell().getState());
        }
    }

    @Nested
    class WorldBoardShouldTest {
        @ParameterizedTest
        @CsvSource({
                "2, 'DEAD#DEAD%DEAD#DEAD', " +
                        "'[DEAD DEAD\n" +
                        "DEAD DEAD]'",
                "2, 'ALIVE#DEAD%DEAD#DEAD', " +
                        "'[DEAD DEAD\n" +
                        "DEAD DEAD]'",
                "2, 'ALIVE#ALIVE%DEAD#DEAD', " +
                        "'[DEAD DEAD\n" +
                        "DEAD DEAD]'",
                "2, 'ALIVE#ALIVE%ALIVE#DEAD', " +
                        "'[ALIVE ALIVE\n" +
                        "ALIVE ALIVE]'",
        })
        void defineNextGenerationCellsFromCurrentCells(
                int worldSize,
                String initialCellStates,
                String expectedCellStates) {

            // Arrange
            ConsoleOutputGenerationBoard outputGenerationBoard = new ConsoleOutputGenerationBoard();
            Cell[][] initialCells = new Cell[worldSize][worldSize];
            String[] rows = initialCellStates.split("%");
            for (int i = 0; i < rows.length; i++) {
                String[] columns = rows[i].split("#");
                for (int j = 0; j < columns.length; j++) {
                    initialCells[i][j] = new Cell(CellState.valueOf(columns[j]));
                }
            }

            // Act
            World sut = new World(initialCells, outputGenerationBoard);
            sut.nextGeneration();

            // Assert
            assertEquals(expectedCellStates, outputGenerationBoard.printed());
        }

        /*
        @ParameterizedTest
        @CsvSource({
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
         */
    }
}
