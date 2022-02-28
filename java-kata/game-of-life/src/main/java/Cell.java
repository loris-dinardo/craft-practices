public class Cell {
    private final CellState state;

    public Cell(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public Cell nextStateWhenNeighborsAliveAre(int aliveNeighbors) {
        if (aliveNeighbors < 2)
            return new Cell(CellState.DEAD);
        return new Cell(state);
    }
}
