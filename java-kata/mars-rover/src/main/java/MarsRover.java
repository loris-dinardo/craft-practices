public class MarsRover {
    private Direction direction;
    private Coordinate coordinate;


    public MarsRover() {
        this.direction = Direction.NORTH;
        coordinate = new Coordinate(0, 0);
    }

    public MarsRover(Direction direction, Coordinate coordinate) {
        this.direction = direction;
        this.coordinate = coordinate;
    }

    public String execute(String[] commands) {
        for (String command : commands) {
            if (command.equals("R")) {
                direction = direction.turnRight();
            }
            if (command.equals("L")) {
                direction = direction.turnLeft();
            }
            if (command.equals("M")) {
                coordinate = move();
            }
        }

        return "" + coordinate.getX() + ":" + coordinate.getY() + ":" + direction.getValue();
    }

    private Coordinate move() {
        if (direction == Direction.NORTH) {
            return new Coordinate(coordinate.getX(), coordinate.getY() + 1);
        }
        if (direction == Direction.EAST) {
            return new Coordinate(coordinate.getX() + 1, coordinate.getY());
        }
        if (direction == Direction.SOUTH) {
            return new Coordinate(coordinate.getX(), coordinate.getY() - 1);
        }
        if (direction == Direction.WEST) {
            return new Coordinate(coordinate.getX() - 1, coordinate.getY());
        }
        return coordinate;
    }
}

