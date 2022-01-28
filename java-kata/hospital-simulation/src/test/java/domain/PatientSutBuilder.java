package domain;

import java.util.List;

public class PatientSutBuilder {
    private Patient sut;

    public static PatientSutBuilder builder() {
        return new PatientSutBuilder();
    }

    public PatientSutBuilder withPatientStat(State state) {
        sut = Patient.withState(state);
        return this;
    }

    public PatientSutBuilder withHealthyPatient() {
        sut = Patient.withState(new HealthyState());
        return this;
    }

    public PatientSutBuilder withDeadPatient() {
        sut = Patient.withState(new DeadState());
        return this;
    }

    public PatientSutBuilder withPatientWithFever() {
        sut = Patient.withState(new FeverState());
        return this;
    }

    public PatientSutBuilder withPatientWithDiabetes() {
        sut = Patient.withState(new DiabetesState());
        return this;
    }

    public PatientSutBuilder withPatientWithTuberculosis() {
        sut = Patient.withState(new TuberculosisState());
        return this;
    }

    public PatientSutBuilder receivingDrugs(List<Drug> drugs) {
        sut.prepareDrugsToBeAdministrated(drugs);
        return this;
    }

    public Patient build() {
        return sut;
    }
}
