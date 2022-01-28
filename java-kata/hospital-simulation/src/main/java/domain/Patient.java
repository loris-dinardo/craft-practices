package domain;

import java.util.List;

public class Patient {
    private final PatientState patientState;
    private List<Drug> receivedDrugs;

    protected Patient(PatientState patientState) {
        this.patientState = patientState;
        this.receivedDrugs = List.of();
    }

    public State getStateAfterDrugsMightHaveBeenAdministrated() {
        return this.patientState.getNextStateWhenDrugsReceived(this.receivedDrugs);
    }

    public void receivesDrugs(List<Drug> drugs) {
        this.receivedDrugs = drugs;
    }
}
