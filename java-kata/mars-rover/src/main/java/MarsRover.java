public class MarsRover {
    private Direction direction;
    private Coordinate coordinate;
    private final int MAX_HEIGHT = 10;
    private final int MAX_WIDTH = 10;

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
                coordinate = coordinate.moveToDirection(direction, MAX_HEIGHT, MAX_WIDTH);
            }
        }

        return "" + coordinate.getX() + ":" + coordinate.getY() + ":" + direction.getValue();
    }
}

