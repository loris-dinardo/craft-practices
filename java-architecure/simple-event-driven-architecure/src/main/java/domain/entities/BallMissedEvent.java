package domain.entities;

import java.util.Objects;

public class BallMissedEvent extends PointEvent {
    private final String missedByPlayerName;
    private final String sentByPlayerName;

    public BallMissedEvent(String uuid, String idPoint, String missedByPlayerName, String sentByPlayerName) {
        super(uuid, idPoint);
        this.missedByPlayerName = missedByPlayerName;
        this.sentByPlayerName = sentByPlayerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallMissedEvent that = (BallMissedEvent) o;
        return this.getUuid().equals(that.getUuid())
                && this.getIdPoint().equals(that.getIdPoint())
                && Objects.equals(missedByPlayerName, that.missedByPlayerName)
                && Objects.equals(sentByPlayerName, that.sentByPlayerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getIdPoint(), missedByPlayerName, sentByPlayerName);
    }
}
