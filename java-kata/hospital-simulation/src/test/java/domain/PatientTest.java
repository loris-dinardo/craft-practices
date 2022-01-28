package domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {
    @Nested
    @Description("Patient who got no previous medication")
    class PatientWhoReceivesNoDrugTestCase {
        @Test
        @Description("Healthy patient should stay healthy when receives no drug")
        public void patientHealthyShouldStayHealthy() {
            // arrange
            Patient sut = PatientFactory.getHealthyPatient();

            // act
            sut.receiveNoDrug();

            // assert
            assertEquals(sut.getState(), PatientState.HEALTHY);
        }

        @Test
        @Description("Patient with fever should stay with fever when receives no drug")
        public void patientWithFeverShouldStayWithFever() {
            // arrange
            Patient sut = PatientFactory.getPatientWithFever();

            // act
            sut.receiveNoDrug();

            // assert
            assertEquals(sut.getState(), PatientState.FEVER);
        }

        @Test
        @Description("Patient with Diabetes should die when receives no drug")
        public void patientWithDiabetesShouldDie() {
            // arrange
            Patient sut = PatientFactory.getPatientWithDiabetes();

            // act
            sut.receiveNoDrug();

            // assert
            assertEquals(sut.getState(), PatientState.DEAD);
        }

        @Test
        @Description("Patient with Tuberculosis should stay with Tuberculosis when receives no drug")
        public void patientWithTuberculosisShouldStayWithTuberculosis() {
            // arrange
            Patient sut = PatientFactory.getPatientWithTuberculosis();

            // act
            sut.receiveNoDrug();

            // assert
            assertEquals(sut.getState(), PatientState.TUBERCULOSIS);
        }

        @Test
        @Description("Dead patient should stay dead when receives no drug")
        public void patientDeadShouldStayDead() {
            // arrange
            Patient sut = PatientFactory.getDeadPatient();

            // act
            sut.receiveNoDrug();

            // assert
            assertEquals(sut.getState(), PatientState.DEAD);
        }
    }
}
