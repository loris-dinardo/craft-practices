package domain;

import java.util.List;

public class Patient {
    private final PatientState state;
    private List<Drug> receivedDrugs;

    protected Patient(PatientState state) {
        this.state = state;
        this.receivedDrugs = List.of();
    }

    public PatientState getStateAfterDrugsMightHaveBeenAdministrated() {
        if (this.receivedDrugs.contains(Drug.PARACETAMOL) && this.receivedDrugs.contains(Drug.ASPIRIN)) {
            return PatientState.DEAD;
        }
        if (this.state.equals(PatientState.DIABETES) && !this.receivedDrugs.contains(Drug.INSULIN)) {
            return PatientState.DEAD;
        }
        if (this.state.equals(PatientState.FEVER) && (this.receivedDrugs.contains(Drug.ASPIRIN) || this.receivedDrugs.contains(Drug.PARACETAMOL))) {
            return PatientState.HEALTHY;
        }
        if (this.state.equals(PatientState.TUBERCULOSIS) && this.receivedDrugs.contains(Drug.ANTIBIOTIC)) {
            return PatientState.HEALTHY;
        }
        if (this.state.equals(PatientState.HEALTHY) && this.receivedDrugs.contains(Drug.ANTIBIOTIC) && this.receivedDrugs.contains(Drug.INSULIN)) {
            return PatientState.FEVER;
        }
        return this.state;
    }

    public void receivesDrugs(List<Drug> drugs) {
        this.receivedDrugs = drugs;
    }
}
