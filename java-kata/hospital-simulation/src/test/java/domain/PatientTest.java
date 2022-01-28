package domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientTest {
    @Nested
    @Description("Patient who receives no drug")
    class PatientWhoReceivesNoDrugTestCase {
        @Test
        @Description("Healthy patient should stay healthy when receives no drug")
        public void patientHealthyShouldStayHealthy() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withHealthyPatient()
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof HealthyState
            );
        }

        @Test
        @Description("Patient with fever should stay with fever when receives no drug")
        public void patientWithFeverShouldStayWithFever() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof FeverState
            );
        }

        @Test
        @Description("Patient with Diabetes should die when receives no drug")
        public void patientWithDiabetesShouldDie() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithDiabetes()
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof DeadState
            );
        }

        @Test
        @Description("Patient with Tuberculosis should stay with Tuberculosis when receives no drug")
        public void patientWithTuberculosisShouldStayWithTuberculosis() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithTuberculosis()
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof TuberculosisState
            );
        }

        @Test
        @Description("Dead patient should stay dead when receives no drug")
        public void patientDeadShouldStayDead() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withDeadPatient()
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof DeadState
            );
        }
    }

    @Nested
    @Description("Patient with fever who receives one drug")
    class PatientWithFeverWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with fever should get healthy when receives aspirin")
        public void patientWithFeverShouldGetHealthyWithAspirin() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .receivingDrugs(List.of(Drug.ASPIRIN))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof HealthyState
            );
        }

        @Test
        @Description("Patient with fever should get healthy when receives paracetamol")
        public void patientWithFeverShouldGetHealthyWithParacetamol() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithFever()
                            .receivingDrugs(List.of(Drug.PARACETAMOL))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof HealthyState
            );
        }
    }

    @Nested
    @Description("Patient with Tuberculosis who receives one drug")
    class PatientWithTuberculosisWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with tuberculosis should get healthy when receives antibiotic")
        public void patientWithTuberculosisShouldGetHealthyWithAntibiotic() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithTuberculosis()
                            .receivingDrugs(List.of(Drug.ANTIBIOTIC))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof HealthyState
            );
        }
    }

    @Nested
    @Description("Patient with Diabetes who receives one drug")
    class PatientWithDiabetesWhoReceivesOneDrugTestCase {
        @Test
        @Description("Patient with diabetes should remain with diabetes when receives insulin")
        public void patientWithDiabetesShouldRemainDiabetesWithInsulin() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientWithDiabetes()
                            .receivingDrugs(List.of(Drug.INSULIN))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof DiabetesState
            );
        }
    }

    @Nested
    @Description("Healthy patient who receives two drugs")
    class PatientHealthyWhoReceivesTwoDrugTestCase {
        @Test
        @Description("Healthy patient should get fever when receives insulin mixed with antibiotic")
        public void patientHealthyShouldGetFeverWithInsulinMixedWithAntibiotic() {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withHealthyPatient()
                            .receivingDrugs(List.of(Drug.INSULIN, Drug.ANTIBIOTIC))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof FeverState
            );
        }
    }

    @Nested
    @Description("Any patient who receives two drugs")
    class PatientAnyWhoReceivesTwoDrugTestCase {
        private void assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(State state) {
            assertTrue(
                    PatientSutBuilder.builder()
                            .withPatientStat(state)
                            .receivingDrugs(List.of(Drug.PARACETAMOL, Drug.ASPIRIN))
                            .build()
                            .stateAfterDrugsWereAdministrated() instanceof DeadState
            );
        }

        @Test
        @Description("Healthy patient should died when receives paracetamol with aspirin")
        public void patientHealthyShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(new HealthyState());
        }

        @Test
        @Description("Patient with fever should died when receives paracetamol with aspirin")
        public void patientWithFeverShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(new FeverState());
        }

        @Test
        @Description("Patient with diabetes should died when receives paracetamol with aspirin")
        public void patientWithDiabetesShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(new DiabetesState());
        }

        @Test
        @Description("Patient with tuberculosis should died when receives paracetamol with aspirin")
        public void patientWithTuberculosisShouldDiedWithParacetamolMixedWithAspirin() {
            this.assertPatientStateDeadWhenReceivingParacetamolMixedWithAspirinWhenState(new TuberculosisState());
        }
    }
}
