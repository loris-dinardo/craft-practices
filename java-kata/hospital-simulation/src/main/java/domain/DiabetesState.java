package domain;

import java.util.List;

public class DiabetesState implements State {
    @Override
    public State stateAfterReceivingDrugs(List<Drug> drugs) {
        if (drugs.contains(Drug.PARACETAMOL) && drugs.contains(Drug.ASPIRIN)) {
            return new DeadState();
        }
        if (!drugs.contains(Drug.INSULIN)) {
            return new DeadState();
        }
        return new DiabetesState();
    }
}
