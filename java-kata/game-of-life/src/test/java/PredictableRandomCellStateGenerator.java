public class PredictableRandomCellStateGenerator implements RandomCellStateGenerator {
    private final CellState predictableNextState;

    public PredictableRandomCellStateGenerator(CellState definedRandomResult) {
        predictableNextState = definedRandomResult;
    }

    @Override
    public CellState getNextState() {
        return this.predictableNextState;
    }
}
