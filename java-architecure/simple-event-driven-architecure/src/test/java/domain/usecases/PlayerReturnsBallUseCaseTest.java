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
            "Player A, eventUuid, idPoint, Player B, true, 'Player A has returned the ball of point idPoint to Player B'",
            "Player A, eventUuid, idPoint, Player B, false, 'Player A missed the ball of point idPoint sent by Player B'"
    })
    void whenPlayerSucceedsToReturnTheBallShouldNotifyOtherPlayer(
            String playerName,
            String eventUuid,
            String idPoint,
            String opponentName,
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
        ).playerTriesToReturnTheBall(new BallSentEvent(eventUuid, idPoint, opponentName, playerName));

        if (playerHasHitTheBall)
            assertTrue(gameEventPublisher.events().contains(new BallSentEvent(eventUuid, idPoint, playerName, opponentName)));
        else
            assertTrue(gameEventPublisher.events().contains(new BallMissedEvent(eventUuid, idPoint, playerName, opponentName)));
        assertEquals(expectedOutput, gameOutputDisplay.printed());
    }
}
