package domain.usecases;

import domain.entities.BallMissedEvent;
import domain.entities.BallSentEvent;
import domain.ports.DoesPlayerHitTheBall;
import domain.ports.GameEventPublisher;
import domain.ports.GameOutputDisplay;

public class PlayerReturnsBallUseCase {
    private final GameEventPublisher gameEventPublisher;
    private final GameOutputDisplay gameOutputDisplay;
    private final DoesPlayerHitTheBall doesPlayerHitTheBall;

    public PlayerReturnsBallUseCase(GameEventPublisher gameEventPublisher,
                                    GameOutputDisplay gameOutputDisplay,
                                    DoesPlayerHitTheBall doesPlayerHitTheBall) {
        this.gameEventPublisher = gameEventPublisher;
        this.gameOutputDisplay = gameOutputDisplay;
        this.doesPlayerHitTheBall = doesPlayerHitTheBall;
    }


    public void playerTriesToReturnTheBall(String playerName, BallSentEvent ballSentEvent) {
        if (doesPlayerHitTheBall.hasHitTheBall()) {
            hasReturnedTheBall(playerName, ballSentEvent);
        } else {
            hasMissedTheBall(playerName, ballSentEvent);
        }

    }

    private void hasMissedTheBall(String playerName, BallSentEvent ballSentEvent) {
        gameEventPublisher.publish(new BallMissedEvent(
                ballSentEvent.getUuid(),
                ballSentEvent.getIdPoint(),
                playerName,
                ballSentEvent.getFromPlayerName())
        );
        gameOutputDisplay.print(playerName + " missed the ball of point "
                + ballSentEvent.getIdPoint() + " sent by " + ballSentEvent.getFromPlayerName());
    }

    private void hasReturnedTheBall(String playerName, BallSentEvent ballSentEvent) {
        gameEventPublisher.publish(BallSentEvent.withReversedPlayersAndFrom(ballSentEvent));
        gameOutputDisplay.print(playerName + " has returned the ball of point "
                + ballSentEvent.getIdPoint() + " to " + ballSentEvent.getFromPlayerName());
    }
}
