import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {
    StringCalculator sut = new StringCalculator();

    @Nested
    class Step1Test {
        @Test
        void whenAddCalledWithEmptyString_ShouldReturnZero() {
            assertEquals(0, sut.add(""));
        }

        @Test
        void whenAddCalledWithOneNumber_ShouldReturnTheNumber() {
            assertEquals(1, sut.add("1"));
            assertEquals(2, sut.add("2"));
            assertEquals(20, sut.add("20"));
            assertEquals(200, sut.add("200"));
        }

        @Test
        void whenAddCalledWithTwoNumbersSeparatedByComma_ShouldReturnTheSumOfNumbers() {
            assertEquals(3, sut.add("1,2"));
            assertEquals(5, sut.add("3,2"));
            assertEquals(50, sut.add("30,20"));
            assertEquals(50, sut.add("3,47"));
        }
    }

    @Nested
    class Step2Test {
        @Test
        void whenAddCalledWithUnknownNumberOfNumbersSeparatedByComma_ShouldReturnTheSumOfNumbers() {
            assertEquals(3, sut.add("1,1,1"));
            assertEquals(10, sut.add("1,2,3,4"));
            assertEquals(100, sut.add("10,20,30,40"));
        }
    }

    @Nested
    class Step3Test {
        @Test
        void whenAddCalledWithUnknownNumberOfNumbersSeparatedByCommaAndNewLine_ShouldReturnTheSumOfNumbers() {
            assertEquals(3, sut.add("1,1\n1"));
            assertEquals(10, sut.add("1,2\n3\n4"));
            assertEquals(100, sut.add("10\n10,10\n30,40"));
        }
    }

    @Nested
    class Step4Test {
        @Test
        void whenAddCalledWithSeparatorAtTheEnd_ShouldThrowAnException() {
            assertThrows(InvalidParameterException.class, () -> sut.add("1,1\n1,"));
            assertThrows(InvalidParameterException.class, () -> sut.add("1,1\n1\n"));
            assertThrows(InvalidParameterException.class, () -> sut.add("10\\n10,10\\n30,40\n"));
        }
    }

    @Nested
    class Step5Test {
        @Test
        void whenAddCalledWithCustomSeparatorAtTheBeginning_ShouldReturnTheSumOfNumbers() {
            assertEquals(3, sut.add("//;\n1;2"));
            assertEquals(3, sut.add("//|\n1|2"));
            assertEquals(3, sut.add("//sep\n1sep2"));
        }

        @Test
        void whenAddCalledWithUnspecifiedSeparator_ShouldThrowAnException() {
            assertThrows(InvalidSeparatorsException.class, () -> sut.add("//;\n1|2"));
            assertThrows(InvalidSeparatorsException.class, () -> sut.add("//|\n10+2|5"));
            assertThrows(InvalidSeparatorsException.class, () -> sut.add("//sep\n10,10sep10,30"));
        }
    }
}
