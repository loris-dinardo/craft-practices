package domain.entities;

import java.util.Objects;

public class EntityA {
    private final String name;

    public EntityA(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityA entityA = (EntityA) o;
        return Objects.equals(name, entityA.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
