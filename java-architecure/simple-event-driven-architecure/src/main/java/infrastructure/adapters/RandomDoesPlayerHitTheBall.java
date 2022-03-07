package infrastructure.adapters;

import domain.ports.DoesPlayerHitTheBall;

import java.util.Random;

public class RandomDoesPlayerHitTheBall implements DoesPlayerHitTheBall {
    private final Random random = new Random();

    @Override
    public boolean hasHitTheBall() {
        return random.nextBoolean();
    }
}
