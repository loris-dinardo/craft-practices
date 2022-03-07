package domain.entities;

public class PointEvent extends DomainEvent {
    private final String idPoint;

    public PointEvent(String uuid, String idPoint) {
        super(uuid);
        this.idPoint = idPoint;
    }

    public String getIdPoint() {
        return idPoint;
    }
}
