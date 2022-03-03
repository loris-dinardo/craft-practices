package infrastructure.adapters;

import domain.ports.UUIDGenerator;

public class InMemoryUUIDGenerator implements UUIDGenerator {
    private final String fixedUUID;

    public InMemoryUUIDGenerator(String fixedUUID) {
        this.fixedUUID = fixedUUID;
    }

    @Override
    public String nextUUID() {
        return fixedUUID;
    }
}
