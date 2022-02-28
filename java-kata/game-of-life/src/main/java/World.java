import java.util.Arrays;

public class World {
    private final CellState[][] worldGrid;
    private final OutputGenerationBoard outputGenerationBoard;

    public World(int sizeX, int sizeY, RandomCellStateGenerator stateGenerator, OutputGenerationBoard outputGenerationBoard) {
        this.worldGrid = new CellState[sizeX][sizeY];
        this.outputGenerationBoard = outputGenerationBoard;
        initWorldBoard(stateGenerator);
    }

    private void initWorldBoard(RandomCellStateGenerator stateGenerator) {
        for (CellState[] cellStates : worldGrid) {
            Arrays.fill(cellStates, stateGenerator.getNextState());
        }
        this.outputGenerationBoard.outputBoard(worldGrid);
    }
}
