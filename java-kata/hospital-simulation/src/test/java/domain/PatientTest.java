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
            assertEquals(
                    State.HEALTHY,
                    PatientSutBuilder.builder()
                            .withHealthyPatient()
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Patient with fever should stay with fever when receives no drug")
        public void patientWithFeverShouldStayWithFever() {
            assertEquals(
                    State.FEVER,
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Patient with Diabetes should die when receives no drug")
        public void patientWithDiabetesShouldDie() {
            assertEquals(
                    State.DEAD,
                    PatientSutBuilder.builder()
                            .withPatientWithDiabetes()
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Patient with Tuberculosis should stay with Tuberculosis when receives no drug")
        public void patientWithTuberculosisShouldStayWithTuberculosis() {
            assertEquals(
                    State.TUBERCULOSIS,
                    PatientSutBuilder.builder()
                            .withPatientWithTuberculosis()
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Dead patient should stay dead when receives no drug")
        public void patientDeadShouldStayDead() {
            assertEquals(
                    State.DEAD,
                    PatientSutBuilder.builder()
                            .withDeadPatient()
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }
    }

    @Nested
    @Description("Patient with fever who receives one drug")
    class PatientWithFeverWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with fever should get healthy when receives aspirin")
        public void patientWithFeverShouldGetHealthyWithAspirin() {
            assertEquals(
                    State.HEALTHY,
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .receivingDrugs(List.of(Drug.ASPIRIN))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Patient with fever should get healthy when receives paracetamol")
        public void patientWithFeverShouldGetHealthyWithParacetamol() {
            assertEquals(
                    State.HEALTHY,
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .receivingDrugs(List.of(Drug.PARACETAMOL))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }
    }

    @Nested
    @Description("Patient with Tuberculosis who receives one drug")
    class PatientWithTuberculosisWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with tuberculosis should get healthy when receives antibiotic")
        public void patientWithTuberculosisShouldGetHealthyWithAntibiotic() {
            assertEquals(
                    State.HEALTHY,
                    PatientSutBuilder.builder()
                            .withPatientWithTuberculosis()
                            .receivingDrugs(List.of(Drug.ANTIBIOTIC))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }
    }

    @Nested
    @Description("Patient with Diabetes who receives one drug")
    class PatientWithDiabetesWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with diabetes should remain with diabetes when receives insulin")
        public void patientWithDiabetesShouldRemainDiabetesWithInsulin() {
            assertEquals(
                    State.DIABETES,
                    PatientSutBuilder.builder()
                            .withPatientWithDiabetes()
                            .receivingDrugs(List.of(Drug.INSULIN))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }
    }

    @Nested
    @Description("Healthy patient who receives two drugs")
    class PatientHealthyWhoReceivesTwoDrugTestCase {
        @Test
        @Description("Healthy patient should get fever when receives insulin mixed with antibiotic")
        public void patientHealthyShouldGetFeverWithInsulinMixedWithAntibiotic() {
            assertEquals(
                    State.FEVER,
                    PatientSutBuilder.builder()
                            .withHealthyPatient()
                            .receivingDrugs(List.of(Drug.INSULIN, Drug.ANTIBIOTIC))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }
    }

    @Nested
    @Description("Any patient who receives two drugs")
    class PatientAnyWhoReceivesTwoDrugTestCase {
        private void assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State state) {
            PatientSutBuilder builder = PatientSutBuilder.builder();
            switch (state) {
                case HEALTHY:
                    builder.withHealthyPatient();
                    break;
                case FEVER:
                    builder.withPatientWithFever();
                    break;
                case DIABETES:
                    builder.withPatientWithDiabetes();
                    break;
                case TUBERCULOSIS:
                    builder.withPatientWithTuberculosis();
                    break;
                default:
                    builder.withDeadPatient();
            }
            assertEquals(
                    State.DEAD,
                    builder
                            .receivingDrugs(List.of(Drug.PARACETAMOL, Drug.ASPIRIN))
                            .build()
                            .getStateAfterDrugsMightHaveBeenAdministrated()
            );
        }

        @Test
        @Description("Healthy patient should died when receives paracetamol with aspirin")
        public void patientHealthyShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State.HEALTHY);
        }

        @Test
        @Description("Patient with fever should died when receives paracetamol with aspirin")
        public void patientWithFeverShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State.FEVER);
        }

        @Test
        @Description("Patient with diabetes should died when receives paracetamol with aspirin")
        public void patientWithDiabetesShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State.DIABETES);
        }

        @Test
        @Description("Patient with tuberculosis should died when receives paracetamol with aspirin")
        public void patientWithTuberculosisShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State.TUBERCULOSIS);
        }
    }
}
