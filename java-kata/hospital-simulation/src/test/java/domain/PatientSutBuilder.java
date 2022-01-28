package domain;

import java.util.List;

public class PatientSutBuilder {
    private Patient sut;

    public static PatientSutBuilder builder() {
        return new PatientSutBuilder();
    }

    public PatientSutBuilder withHealthyPatient() {
        sut = PatientFactory.getHealthyPatient();
        return this;
    }

    public PatientSutBuilder withDeadPatient() {
        sut = PatientFactory.getDeadPatient();
        return this;
    }

    public PatientSutBuilder withPatientWithFever() {
        sut = PatientFactory.getPatientWithFever();
        return this;
    }

    public PatientSutBuilder withPatientWithDiabetes() {
        sut = PatientFactory.getPatientWithDiabetes();
        return this;
    }

    public PatientSutBuilder withPatientWithTuberculosis() {
        sut = PatientFactory.getPatientWithTuberculosis();
        return this;
    }

    public PatientSutBuilder receivingDrugs(List<Drug> drugs) {
        sut.receivesDrugs(drugs);
        return this;
    }

    public Patient build() {
        return sut;
    }
}
