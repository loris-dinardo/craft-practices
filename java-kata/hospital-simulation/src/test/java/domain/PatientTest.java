package domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

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
            assertEquals(PatientState.HEALTHY, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }

        @Test
        @Description("Patient with fever should stay with fever when receives no drug")
        public void patientWithFeverShouldStayWithFever() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // assert
            assertEquals(PatientState.FEVER, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }

        @Test
        @Description("Patient with Diabetes should die when receives no drug")
        public void patientWithDiabetesShouldDie() {
            // arrange
            Patient sut = PatientFactory.getPatientWithDiabetes();

            // assert
            assertEquals(PatientState.DEAD, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }

        @Test
        @Description("Patient with Tuberculosis should stay with Tuberculosis when receives no drug")
        public void patientWithTuberculosisShouldStayWithTuberculosis() {
            // arrange
            Patient sut = PatientFactory.getPatientWithTuberculosis();

            // assert
            assertEquals(PatientState.TUBERCULOSIS, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }

        @Test
        @Description("Dead patient should stay dead when receives no drug")
        public void patientDeadShouldStayDead() {
            // arrange
            Patient sut = PatientFactory.getDeadPatient();

            // assert
            assertEquals(PatientState.DEAD, sut.getStateAfterDrugsMightHaveBeenAdministrated());
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
            sut.receivesDrugs(List.of(Drug.ASPIRIN));

            // assert
            assertEquals(PatientState.HEALTHY, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }

        @Test
        @Description("Patient with fever should get healthy when receives paracetamol")
        public void patientWithFeverShouldGetHealthyWithParacetamol() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // act
            sut.receivesDrugs(List.of(Drug.PARACETAMOL));

            // assert
            assertEquals(PatientState.HEALTHY, sut.getStateAfterDrugsMightHaveBeenAdministrated());
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
            sut.receivesDrugs(List.of(Drug.ANTIBIOTIC));

            // assert
            assertEquals(PatientState.HEALTHY, sut.getStateAfterDrugsMightHaveBeenAdministrated());
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
            sut.receivesDrugs(List.of(Drug.INSULIN));

            // assert
            assertEquals(PatientState.DIABETES, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }
    }

    @Nested
    @Description("Healthy patient who receives two drugs")
    class PatientHealthyWhoReceivesTwoDrugTestCase {
        @Test
        @Description("Healthy patient should get fever when receives insulin mixed with antibiotic")
        public void patientHealthyShouldGetFeverWithInsulinMixedWithAntibiotic() {
            // arrange
            Patient sut = PatientFactory.getHealthyPatient();

            // act
            sut.receivesDrugs(List.of(Drug.INSULIN, Drug.ANTIBIOTIC));

            // assert
            assertEquals(PatientState.FEVER, sut.getStateAfterDrugsMightHaveBeenAdministrated());
        }
    }

    @Nested
    @Description("Any patient who receives two drugs")
    class PatientAnyWhoReceivesTwoDrugTestCase {
        private void assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(PatientState state) {
            Patient sut;
            switch (state) {
                case HEALTHY:
                    sut = PatientFactory.getHealthyPatient();
                    break;
                case FEVER:
                    sut = PatientFactory.getPatientWithFever();
                    break;
                case DIABETES:
                    sut = PatientFactory.getPatientWithDiabetes();
                    break;
                case TUBERCULOSIS:
                    sut = PatientFactory.getPatientWithTuberculosis();
                    break;
                default:
                    sut = PatientFactory.getDeadPatient();
            }
            // act
            sut.receivesDrugs(List.of(Drug.PARACETAMOL, Drug.ASPIRIN));

            // assert
            assertEquals(PatientState.DEAD, sut.getStateAfterDrugsMightHaveBeenAdministrated());

        }


        @Test
        @Description("Healthy patient should died when receives paracetamol with aspirin")
        public void patientHealthyShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(PatientState.HEALTHY);
        }

        @Test
        @Description("Patient with fever should died when receives paracetamol with aspirin")
        public void patientWithFeverShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(PatientState.FEVER);
        }

        @Test
        @Description("Patient with diabetes should died when receives paracetamol with aspirin")
        public void patientWithDiabetesShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(PatientState.DIABETES);
        }

        @Test
        @Description("Patient with tuberculosis should died when receives paracetamol with aspirin")
        public void patientWithTuberculosisShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(PatientState.TUBERCULOSIS);
        }
    }
}
