package domain;

public class PatientFactory {
    public static Patient getHealthyPatient() {
        return new Patient(PatientState.HEALTHY);
    }

    public static Patient getPatientWithFever() {
        return new Patient(PatientState.FEVER);
    }

    public static Patient getPatientWithDiabetes() {
        return new Patient(PatientState.DIABETES);
    }

    public static Patient getPatientWithTuberculosis() {
        return new Patient(PatientState.TUBERCULOSIS);
    }

    public static Patient getDeadPatient() {
        return new Patient(PatientState.DEAD);
    }
}
