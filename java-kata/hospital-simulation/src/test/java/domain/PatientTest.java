package domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {
    @Nested
    @Description("Patient who receives no drug")
    class PatientWhoReceivesNoDrugTestCase {
        @Test
        @Description("Healthy patient should stay healthy when receives no drug")
        public void patientHealthyShouldStayHealthy() {
            // arrange
            Patient sut = PatientFactory.getHealthyPatient();

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.HEALTHY);
        }

        @Test
        @Description("Patient with fever should stay with fever when receives no drug")
        public void patientWithFeverShouldStayWithFever() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.FEVER);
        }

        @Test
        @Description("Patient with Diabetes should die when receives no drug")
        public void patientWithDiabetesShouldDie() {
            // arrange
            Patient sut = PatientFactory.getPatientWithDiabetes();

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.DEAD);
        }

        @Test
        @Description("Patient with Tuberculosis should stay with Tuberculosis when receives no drug")
        public void patientWithTuberculosisShouldStayWithTuberculosis() {
            // arrange
            Patient sut = PatientFactory.getPatientWithTuberculosis();

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.TUBERCULOSIS);
        }

        @Test
        @Description("Dead patient should stay dead when receives no drug")
        public void patientDeadShouldStayDead() {
            // arrange
            Patient sut = PatientFactory.getDeadPatient();

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.DEAD);
        }
    }

    @Nested
    @Description("Patient with fever who receives one drug")
    class PatientWithFeverWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with fever should get healthy when receives aspirin")
        public void patientWithFeverShouldGetHealthyWithAspirin() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // act
            sut.receivesDrug(Drug.ASPIRIN);

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.HEALTHY);
        }

        @Test
        @Description("Patient with fever should get healthy when receives paracetamol")
        public void patientWithFeverShouldGetHealthyWithParacetamol() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // act
            sut.receivesDrug(Drug.PARACETAMOL);

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.HEALTHY);
        }
    }

    @Nested
    @Description("Patient with Tuberculosis who receives one drug")
    class PatientWithTuberculosisWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with tuberculosis should get healthy when receives antibiotic")
        public void patientWithTuberculosisShouldGetHealthyWithAntibiotic() {
            // arrange
            Patient sut = PatientFactory.getPatientWithTuberculosis();

            // act
            sut.receivesDrug(Drug.ANTIBIOTIC);

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.HEALTHY);
        }
    }

    @Nested
    @Description("Patient with Diabetes who receives one drug")
    class PatientWithDiabetesWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with diabetes should remain with diabetes when receives insulin")
        public void patientWithDiabetesShouldRemainDiabetesWithInsulin() {
            // arrange
            Patient sut = PatientFactory.getPatientWithDiabetes();

            // act
            sut.receivesDrug(Drug.INSULIN);

            // assert
            assertEquals(sut.getStateAfterDrugsMightHaveBeenAdministrated(), PatientState.DIABETES);
        }
    }
}
