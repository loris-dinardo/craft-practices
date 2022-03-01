import java.util.Collections;
import java.util.List;

public class Cell {
    private final CellState state;
    private List<Cell> neighbors;

    public Cell(CellState state) {
        this(state, Collections.emptyList());
    }

    public Cell(CellState state, List<Cell> neighbors) {
        this.state = state;
        this.neighbors = neighbors;
    }

    public CellState getState() {
        return state;
    }

    private CellState nextGenerationStateAccordingToNumberOfNeighborsAlive(long aliveNeighbors) {
        if (state == CellState.DEAD && aliveNeighbors == 3)
            return CellState.ALIVE;
        if (aliveNeighbors < 2 | aliveNeighbors > 3)
            return CellState.DEAD;
        return state;
    }

    public Cell nextGenerationCell() {
        long numberOfAliveNeighbors = neighbors.stream().filter(cell -> cell.getState() == CellState.ALIVE).count();
        return new Cell(nextGenerationStateAccordingToNumberOfNeighborsAlive(numberOfAliveNeighbors));
    }

    public void updateNeighbors(List<Cell> neighborsOfCell) {
        this.neighbors = neighborsOfCell;
    }
}
