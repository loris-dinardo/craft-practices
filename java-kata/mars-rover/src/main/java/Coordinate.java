public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate moveToDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return new Coordinate(x, y + 1);
        }
        if (direction == Direction.EAST) {
            return new Coordinate(x + 1, y);
        }
        if (direction == Direction.SOUTH) {
            return new Coordinate(x, y - 1);
        }
        if (direction == Direction.WEST) {
            return new Coordinate(x - 1, y);
        }

        return new Coordinate(x,y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
