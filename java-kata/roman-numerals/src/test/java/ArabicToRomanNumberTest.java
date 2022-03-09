import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArabicToRomanNumberTest {
    @ParameterizedTest
    @CsvSource({
            "1, I",
    })
    void shouldConvertArabicToRomanNumber(
            int arabicNumber,
            String expectedRomanNumber
    ){
        assertEquals(expectedRomanNumber, new ArabicToRomanNumberUseCase().execute(arabicNumber));
    }
}
