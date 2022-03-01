import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PredictableRandomCellStateGenerator implements RandomCellStateGenerator {
    private final List<CellState> sequence;
    private int index = 0;

    public PredictableRandomCellStateGenerator(String definedRandomResultSequence) {
        this.sequence =
                Arrays.stream(definedRandomResultSequence.split("#"))
                        .map(CellState::valueOf).collect(Collectors.toList());
    }

    @Override
    public CellState getNextState() {
        index = index % sequence.size();
        return sequence.get(index++);
    }
}
