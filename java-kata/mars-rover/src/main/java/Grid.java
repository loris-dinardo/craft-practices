import java.util.Collections;
import java.util.List;

public class Grid {
    private final int maxHeight;
    private final int maxWidth;
    private final List<Coordinates> obstacles;

    private Grid(int maxHeight, int maxWidth, List<Coordinates> obstacles) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.obstacles = obstacles;
    }

    public static Grid defaultGrid() {
        return new Grid(10, 10, Collections.emptyList());
    }

    public static Grid withLimitAndObstacles(int maxHeight, int maxWidth, List<Coordinates> obstacles) {
        return new Grid(maxHeight, maxWidth, obstacles);
    }

    public Coordinates nextCoordinateFor(Coordinates coordinates, Direction movingToDirection) {
        Coordinates nextCoordinates = coordinates.stayInPlace();
        if (movingToDirection == Direction.NORTH) {
            nextCoordinates = coordinates.increaseY(maxHeight);
        }
        if (movingToDirection == Direction.EAST) {
            nextCoordinates = coordinates.increaseX(maxWidth);
        }
        if (movingToDirection == Direction.SOUTH) {
            nextCoordinates = coordinates.decreaseY(maxHeight);
        }
        if (movingToDirection == Direction.WEST) {
            nextCoordinates = coordinates.decreaseX(maxWidth);
        }
        return obstacles.contains(nextCoordinates) ? coordinates.stayInPlace() : nextCoordinates;
    }
}
