package domain;

import java.util.List;

public interface State {
    State stateAfterReceivingDrugs(List<Drug> drugs);
}
