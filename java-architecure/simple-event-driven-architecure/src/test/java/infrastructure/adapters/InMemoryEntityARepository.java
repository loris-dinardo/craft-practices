package infrastructure.adapters;

import domain.entities.EntityA;
import domain.ports.EntityARepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryEntityARepository implements EntityARepository {
    private final Map<String, EntityA> entities = new HashMap<>();

    @Override
    public void save(EntityA entityA) {
        this.entities.put(entityA.getName(), entityA);
    }

    public EntityA byName(String name) {
        return entities.get(name);
    }
}
