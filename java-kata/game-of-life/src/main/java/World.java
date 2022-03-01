import java.util.ArrayList;
import java.util.List;

public class World {
    private final Cell[][] worldGrid;
    private final OutputGenerationBoard outputGenerationBoard;

    public World(Cell[][] initialCells, OutputGenerationBoard outputGenerationBoard) {
        this.outputGenerationBoard = outputGenerationBoard;
        this.worldGrid = initialCells;
    }

    public void nextGeneration() {
        notifyCellsAboutTheirNeighbors();
        for (int i = 0; i < worldGrid.length; i++) {
            for (int j = 0; j < worldGrid[i].length; j++) {
                worldGrid[i][j] = worldGrid[i][j].nextGenerationCell();
            }
        }
        this.outputGenerationBoard.outputBoard(worldGrid);
    }

    private void notifyCellsAboutTheirNeighbors() {
        for (int row = 0; row < worldGrid.length; row++) {
            for (int column = 0; column < worldGrid[row].length; column++) {
                worldGrid[row][column].updateNeighbors(getNeighborsOfCell(row, column));
            }
        }
    }

    private List<Cell> getNeighborsOfCell(int row, int column) {
        List<Cell> result = new ArrayList<>();
        for (int rowTmp = row - 1; rowTmp <= row + 1; rowTmp++) {
            for (int columnTmp = column - 1; columnTmp <= column + 1; columnTmp++) {
                if (rowTmp >= 0 && rowTmp < worldGrid.length) {
                    if (columnTmp >= 0 && columnTmp < worldGrid.length) {
                        if (rowTmp != row | columnTmp != column)
                            result.add(worldGrid[rowTmp][columnTmp]);
                    }
                }
            }
        }
        return result;
    }
}
