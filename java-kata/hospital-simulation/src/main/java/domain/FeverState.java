package domain;

import java.util.List;

public class FeverState implements State {
    @Override
    public State stateAfterReceivingDrugs(List<Drug> drugs) {
        if (drugs.contains(Drug.PARACETAMOL) && drugs.contains(Drug.ASPIRIN)) {
            return new DeadState();
        }
        if (drugs.contains(Drug.ASPIRIN) || drugs.contains(Drug.PARACETAMOL)) {
            return new HealthyState();
        }
        return new FeverState();
    }
}
