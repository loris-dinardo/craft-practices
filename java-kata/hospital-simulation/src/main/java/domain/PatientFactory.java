package domain;

public class PatientFactory {
    public static Patient getHealthyPatient() {
        return new Patient(PatientState.of(State.HEALTHY));
    }

    public static Patient getPatientWithFever() {
        return new Patient(PatientState.of(State.FEVER));
    }

    public static Patient getPatientWithDiabetes() {
        return new Patient(PatientState.of(State.DIABETES));
    }

    public static Patient getPatientWithTuberculosis() {
        return new Patient(PatientState.of(State.TUBERCULOSIS));
    }

    public static Patient getDeadPatient() {
        return new Patient(PatientState.of(State.DEAD));
    }
}
