package application;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HospitalSimulatorTest {
    @Test
    @Description("Test should fail")
    public void shouldFail() {
        assertEquals(true, false);
    }
}
