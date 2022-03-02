import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WonderfulWordDetectorUseCaseTest {
    /**
     * Rule of wonderful word detector
     * 1) When user send a palindrome, the user is congratulated
     * in spanish sending him/her ¡Bonita palabra!
     */

    @ParameterizedTest
    @CsvSource({
            "Hola, ''",
            "oto, '¡Bonita palabra!'",
            "Oto, ''",
            "kayak, '¡Bonita palabra!'",
            "22022022, '¡Bonita palabra!'",
    })
    void shouldCongratulateUserWhenTheWordSentIsPalindrome(
            String wordSentByUser,
            String expectedCongratulation
    ){
        assertEquals(expectedCongratulation, new WonderfulWordDetectorUseCase().execute(wordSentByUser));
    }
}
