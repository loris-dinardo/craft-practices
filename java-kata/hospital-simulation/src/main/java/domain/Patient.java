package domain;

import java.util.List;

public class Patient {
    private final State patientState;
    private List<Drug> drugsToBeReceived;

    private Patient(State patientState) {
        this.patientState = patientState;
        this.drugsToBeReceived = List.of();
    }

    public static Patient withState(State initialState) {
        return new Patient(initialState);
    }

    public State stateAfterDrugsWereAdministrated() {
        return this.patientState.stateAfterReceivingDrugs(this.drugsToBeReceived);
    }

    public void prepareDrugsToBeAdministrated(List<Drug> drugs) {
        this.drugsToBeReceived = drugs;
    }
}
