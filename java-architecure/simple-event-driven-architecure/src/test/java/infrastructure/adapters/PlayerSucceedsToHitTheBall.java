package infrastructure.adapters;

import domain.ports.DoesPlayerHitTheBall;

public class PlayerSucceedsToHitTheBall implements DoesPlayerHitTheBall {
    @Override
    public boolean hasHitTheBall() {
        return true;
    }
}
