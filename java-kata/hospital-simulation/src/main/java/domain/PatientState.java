package domain;

import java.util.List;

public class PatientState {
    private final State state;

    private PatientState(State state) {
        this.state = state;
    }

    public static PatientState of(State state) {
        return new PatientState(state);
    }

    public State getNextStateWhenDrugsReceived(List<Drug> drugs) {
        if (drugs.contains(Drug.PARACETAMOL) && drugs.contains(Drug.ASPIRIN)) {
            return State.DEAD;
        }
        if (this.state.equals(State.DIABETES) && !drugs.contains(Drug.INSULIN)) {
            return State.DEAD;
        }
        if (this.state.equals(State.FEVER) && (drugs.contains(Drug.ASPIRIN) || drugs.contains(Drug.PARACETAMOL))) {
            return State.HEALTHY;
        }
        if (this.state.equals(State.TUBERCULOSIS) && drugs.contains(Drug.ANTIBIOTIC)) {
            return State.HEALTHY;
        }
        if (this.state.equals(State.HEALTHY) && drugs.contains(Drug.ANTIBIOTIC) && drugs.contains(Drug.INSULIN)) {
            return State.FEVER;
        }
        return this.state;
    }
}
