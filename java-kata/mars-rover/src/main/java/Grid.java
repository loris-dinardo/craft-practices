public class Grid {
    private final int maxHeight;
    private final int maxWidth;

    public Grid(int maxHeight, int maxWidth) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    public Coordinate nextCoordinateFor(Coordinate coordinate, Direction movingToDirection) {
        if (movingToDirection == Direction.NORTH) {
            return coordinate.increaseY(maxHeight);
        }
        if (movingToDirection == Direction.EAST) {
            return coordinate.increaseX(maxWidth);
        }
        if (movingToDirection == Direction.SOUTH) {
            return coordinate.decreaseY(maxHeight);
        }
        if (movingToDirection == Direction.WEST) {
            return coordinate.decreaseX(maxWidth);
        }

        return coordinate.stayInPlace();
    }
}
