public class Cell {
    private final CellState state;

    public Cell(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public Cell nextGenerationStateWhenNumberOfNeighborsAliveIs(int aliveNeighbors) {
        if (state == CellState.DEAD && aliveNeighbors == 3)
            return new Cell(CellState.ALIVE);
        if (aliveNeighbors < 2 | aliveNeighbors > 3)
            return new Cell(CellState.DEAD);
        return new Cell(state);
    }
}
