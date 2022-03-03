package domain.usecases;

import domain.entities.EntityA;
import domain.entities.EntityACreatedEvent;
import infrastructure.adapters.InMemoryEntityARepository;
import infrastructure.adapters.InMemoryEventPublisher;
import infrastructure.adapters.InMemoryUUIDGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCaseATest {

    @Test
    void shouldCreateAndNotifyAboutEntityACreation_WhenUseCaseExecuted() {
        String uuid = "entityA-uuid";
        String entityName = "EntityName";
        InMemoryEntityARepository entityARepository = new InMemoryEntityARepository();
        InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();
        InMemoryUUIDGenerator uuidGenerator = new InMemoryUUIDGenerator(uuid);

        new UseCaseA(entityARepository, eventPublisher, uuidGenerator).execute(entityName);

        assertTrue(eventPublisher.events().contains(new EntityACreatedEvent(uuid, entityName)));
        assertEquals(new EntityA(entityName), entityARepository.byName(entityName));
    }
}
