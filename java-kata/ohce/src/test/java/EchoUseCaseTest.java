import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoUseCaseTest {
    /**
     *  Rules of echo
     *  1) Should return the word sent by user but in reverse order
     */

    @ParameterizedTest
    @CsvSource({
            "echo, ohce",
            "Hola!, !aloH",
            "Hola amigo!, !ogima aloH"
    })
    void shouldReturnTheWorldSentByUserInReverse(
            String worldSentByUser,
            String expectedWorldRevere
    ){
        assertEquals(expectedWorldRevere, new EchoUseCase().execute(worldSentByUser));
    }
}
