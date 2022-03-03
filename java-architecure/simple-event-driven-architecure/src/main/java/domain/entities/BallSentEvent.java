package domain.entities;

import java.util.Objects;

public class BallSentEvent extends DomainEvent {
    private final String idPoint;

    public BallSentEvent(String uuid, String idPoint) {
        super(uuid);
        this.idPoint = idPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallSentEvent that = (BallSentEvent) o;
        return idPoint.equals(that.idPoint) && this.getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), idPoint);
    }
}
