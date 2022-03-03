package domain.ports;

import domain.entities.EntityA;

public interface EntityARepository {
    void save(EntityA entityA);
}
