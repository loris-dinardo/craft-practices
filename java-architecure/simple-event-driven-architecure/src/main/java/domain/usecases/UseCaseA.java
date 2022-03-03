package domain.usecases;

import domain.entities.EntityA;
import domain.entities.EntityACreatedEvent;
import domain.ports.EntityARepository;
import domain.ports.EventPublisher;
import domain.ports.UUIDGenerator;

public class UseCaseA {
    private final EventPublisher eventPublisher;
    private final UUIDGenerator uuidGenerator;
    private final EntityARepository entityARepository;

    public UseCaseA(EntityARepository entityARepository, EventPublisher eventPublisher, UUIDGenerator uuidGenerator) {
        this.entityARepository = entityARepository;
        this.eventPublisher = eventPublisher;
        this.uuidGenerator = uuidGenerator;
    }

    public void execute(String entityName) {
        // Business logic...
        // ....
        this.entityARepository.save(new EntityA(entityName));
        // ....
        this.eventPublisher.publish(new EntityACreatedEvent(uuidGenerator.nextUUID(), entityName));
    }
}
