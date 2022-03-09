import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArabicToRomanNumberTest {
    @ParameterizedTest
    @CsvSource({
            "1, I",
            "3, III",
            "4, IV",
            "5, V",
            "9, IX",
            "10, X",
            "28, XXVIII",
            "49, XLIX",
            "54, LIV",
            "99, XCIX",
            "127, CXXVII",
            "203, CCIII",
            "449, CDXLIX",
            "549, DXLIX",
            "999, CMXCIX",
            "1999, MCMXCIX"
    })
    void shouldConvertArabicToRomanNumber(
            int arabicNumber,
            String expectedRomanNumber
    ){
        assertEquals(expectedRomanNumber, new ArabicToRomanNumberUseCase().execute(arabicNumber));
    }
}
