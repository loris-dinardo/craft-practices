package domain;

public class Patient {
    private final PatientState state;
    private Drug receivedDrug;

    protected Patient(PatientState state) {
        this.state = state;
        this.receivedDrug = Drug.NONE;
    }

    public PatientState getStateAfterDrugsMightHaveBeenAdministrated() {
        if (this.state.equals(PatientState.DIABETES) && !this.receivedDrug.equals(Drug.INSULIN)) {
            return PatientState.DEAD;
        }
        if (this.state.equals(PatientState.FEVER) && (this.receivedDrug.equals(Drug.ASPIRIN) || this.receivedDrug.equals(Drug.PARACETAMOL))) {
            return PatientState.HEALTHY;
        }
        if (this.state.equals(PatientState.TUBERCULOSIS) && this.receivedDrug.equals(Drug.ANTIBIOTIC)) {
            return PatientState.HEALTHY;
        }
        return this.state;
    }

    public void receivesDrug(Drug drug) {
        this.receivedDrug = drug;
    }
}
