package infrastructure.adapters;

import domain.ports.DoesPlayerHitTheBall;

public class PlayerFailsToHitTheBall implements DoesPlayerHitTheBall {
    @Override
    public boolean hasHitTheBall() {
        return false;
    }
}
