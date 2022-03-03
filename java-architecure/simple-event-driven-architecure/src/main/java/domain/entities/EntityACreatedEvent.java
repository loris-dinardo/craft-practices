package domain.entities;

import java.util.Objects;

public class EntityACreatedEvent extends DomainEvent {
    private final String entityName;

    public EntityACreatedEvent(String uuid, String entityName) {
        super(uuid);
        this.entityName = entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityACreatedEvent that = (EntityACreatedEvent) o;
        return entityName.equals(that.entityName) && this.getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, getUuid());
    }
}
