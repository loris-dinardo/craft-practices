package domain.usecases;

import domain.entities.BallMissedEvent;
import domain.entities.BallSentEvent;
import domain.ports.DoesPlayerHitTheBall;
import infrastructure.adapters.InMemoryGameEventPublisher;
import infrastructure.adapters.InMemoryGameOutputDisplay;
import infrastructure.adapters.PlayerFailsToHitTheBall;
import infrastructure.adapters.PlayerSucceedsToHitTheBall;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerReturnsBallUseCaseTest {
    @ParameterizedTest
    @CsvSource({
            "Player A, eventUuid, idPoint, true, 'Player A has returned the ball of point idPoint'",
            "Player A, eventUuid, idPoint, false, 'Player A missed the ball of point idPoint'"
    })
    void whenPlayerSucceedsToReturnTheBallShouldNotifyOtherPlayer(
            String playerName,
            String eventUuid,
            String idPoint,
            boolean playerHasHitTheBall,
            String expectedOutput
    ) {
        InMemoryGameEventPublisher gameEventPublisher = new InMemoryGameEventPublisher();
        InMemoryGameOutputDisplay gameOutputDisplay = new InMemoryGameOutputDisplay();
        DoesPlayerHitTheBall doesPlayerHitTheBall = playerHasHitTheBall
                ? new PlayerSucceedsToHitTheBall()
                : new PlayerFailsToHitTheBall();

        new PlayerReturnsBallUseCase(
                gameEventPublisher, gameOutputDisplay, doesPlayerHitTheBall
        ).playerTriesToReturnTheBall(playerName, new BallSentEvent(eventUuid, idPoint));

        if (playerHasHitTheBall)
            assertTrue(gameEventPublisher.events().contains(new BallSentEvent(eventUuid, idPoint)));
        else
            assertTrue(gameEventPublisher.events().contains(new BallMissedEvent(eventUuid, idPoint)));
        assertEquals(expectedOutput, gameOutputDisplay.printed());
    }
}
