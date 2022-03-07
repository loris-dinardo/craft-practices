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


    public void playerTriesToReturnTheBall(BallSentEvent ballSentEvent) {
        if (doesPlayerHitTheBall.hasHitTheBall()) {
            hasReturnedTheBall(ballSentEvent);
        } else {
            hasMissedTheBall(ballSentEvent);
        }

    }

    private void hasMissedTheBall(BallSentEvent ballSentEvent) {
        gameEventPublisher.publish(new BallMissedEvent(
                ballSentEvent.getUuid(),
                ballSentEvent.getIdPoint(),
                ballSentEvent.getToPlayerName(),
                ballSentEvent.getFromPlayerName())
        );
        gameOutputDisplay.print(ballSentEvent.getToPlayerName() + " missed the ball of point "
                + ballSentEvent.getIdPoint() + " sent by " + ballSentEvent.getFromPlayerName());
    }

    private void hasReturnedTheBall(BallSentEvent ballSentEvent) {
        gameEventPublisher.publish(BallSentEvent.withReversedPlayersAndFrom(ballSentEvent));
        gameOutputDisplay.print(ballSentEvent.getToPlayerName() + " has returned the ball of point "
                + ballSentEvent.getIdPoint() + " to " + ballSentEvent.getFromPlayerName());
    }
}
