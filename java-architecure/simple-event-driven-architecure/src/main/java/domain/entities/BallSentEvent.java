package domain.entities;

import java.util.Objects;

public class BallSentEvent extends DomainEvent {
    private final String idPoint;
    private final String fromPlayerName;
    private final String toPlayerName;

    public BallSentEvent(String uuid, String idPoint, String fromPlayerName, String toPlayerName) {
        super(uuid);
        this.idPoint = idPoint;
        this.fromPlayerName = fromPlayerName;
        this.toPlayerName = toPlayerName;
    }

    public static BallSentEvent withReversedPlayersAndFrom(BallSentEvent ballSentEvent) {
        return new BallSentEvent(
                ballSentEvent.getUuid(),
                ballSentEvent.getIdPoint(),
                ballSentEvent.getToPlayerName(),
                ballSentEvent.getFromPlayerName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallSentEvent that = (BallSentEvent) o;
        return Objects.equals(idPoint, that.idPoint)
                && this.getUuid().equals(that.getUuid())
                && Objects.equals(fromPlayerName, that.fromPlayerName)
                && Objects.equals(toPlayerName, that.toPlayerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), idPoint, fromPlayerName, toPlayerName);
    }

    public String getIdPoint() {
        return idPoint;
    }

    public String getFromPlayerName() {
        return fromPlayerName;
    }

    public String getToPlayerName() {
        return toPlayerName;
    }
}
