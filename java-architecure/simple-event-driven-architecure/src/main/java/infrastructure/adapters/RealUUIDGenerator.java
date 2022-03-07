package infrastructure.adapters;

import domain.ports.UUIDGenerator;

import java.util.UUID;

public class RealUUIDGenerator implements UUIDGenerator {
    @Override
    public String nextUUID() {
        return UUID.randomUUID().toString();
    }
}
