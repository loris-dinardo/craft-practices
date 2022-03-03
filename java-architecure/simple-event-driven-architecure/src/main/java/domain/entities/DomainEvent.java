package domain.entities;

public class DomainEvent {
    private final String uuid;

    public DomainEvent(String uuid) {
        this.uuid = uuid;
    }

    protected String getUuid() {
        return uuid;
    }
}
