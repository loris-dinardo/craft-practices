public class World {
    private final Cell[][] worldGrid;
    private final OutputGenerationBoard outputGenerationBoard;

    public World(int sizeX, int sizeY, RandomCellStateGenerator stateGenerator,
                 OutputGenerationBoard outputGenerationBoard) {
        this.worldGrid = new Cell[sizeX][sizeY];
        this.outputGenerationBoard = outputGenerationBoard;
        initWorldBoard(stateGenerator);
    }

    private void initWorldBoard(RandomCellStateGenerator stateGenerator) {
        for (Cell[] cellRow : worldGrid) {
            for (int i = 0; i < cellRow.length; i++) {
                cellRow[i] = new Cell(stateGenerator.getNextState());
            }
        }
        this.outputGenerationBoard.outputBoard(worldGrid);
    }

    public void nextGeneration() {
        Cell[][] nextWorldGrid = worldGrid.clone();
        for (int i = 0; i < worldGrid.length; i++) {
            for (int j = 0; j < worldGrid[i].length; j++) {
                nextWorldGrid[i][j] = worldGrid[i][j].nextStateWhenNeighborsAliveAre(getAliveNeighbors(i, j));
            }
        }
        this.outputGenerationBoard.outputBoard(nextWorldGrid);
    }

    private int getAliveNeighbors(int row, int column) {
        int aliveNeighbors = 0;
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = column - 1; j < column + 2; j++) {
                if (neighborIsAlive(i, j))
                    aliveNeighbors++;
            }
        }
        return aliveNeighbors;
    }

    private boolean neighborIsAlive(int row, int column) {
        if (row >= 0 && row < worldGrid.length) {
            if (column >= 0 && column < worldGrid[row].length) {
                return worldGrid[row][column].getState() == CellState.ALIVE;
            }
        }
        return false;
    }
}
