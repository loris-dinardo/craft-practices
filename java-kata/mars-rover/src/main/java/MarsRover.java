public class MarsRover {
    private Direction direction;
    private Coordinate coordinate;
    private final Grid grid;

    public MarsRover() {
        direction = Direction.NORTH;
        coordinate = new Coordinate(0, 0);
        grid = new Grid(10, 10);
    }

    public MarsRover(Direction direction, Coordinate coordinate) {
        this.direction = direction;
        this.coordinate = coordinate;
        grid = new Grid(10, 10);
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
                coordinate = grid.nextCoordinateFor(coordinate, direction);
            }
        }

        return "" + coordinate.getX() + ":" + coordinate.getY() + ":" + direction.getValue();
    }
}

