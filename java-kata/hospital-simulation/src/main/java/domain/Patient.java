package domain;

public class Patient {
    private PatientState state;

    protected Patient(PatientState state) {
        this.state = state;
    }

    public PatientState getState() {
        return this.state;
    }

    public void receiveNoDrug() {
        if (this.state.equals(PatientState.DIABETES)) {
            this.state = PatientState.DEAD;
        }
    }
}
