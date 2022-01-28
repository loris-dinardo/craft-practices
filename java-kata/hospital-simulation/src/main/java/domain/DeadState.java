package domain;

import java.util.List;

public class DeadState implements State {
    @Override
    public State stateAfterReceivingDrugs(List<Drug> drugs) {
        return new DeadState();
    }
}
