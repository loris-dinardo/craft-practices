public class MarsRover {
    private Direction direction;
    private Coordinates coordinates;
    private final Grid grid;

    public MarsRover() {
        this(Direction.NORTH, new Coordinates(0, 0));
    }

    public MarsRover(Direction direction, Coordinates coordinates) {
        this(direction, coordinates, Grid.defaultGrid());
    }

    public MarsRover(Direction direction, Coordinates coordinates, Grid grid) {
        this.direction = direction;
        this.coordinates = coordinates;
        this.grid = grid;
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
                coordinates = grid.nextCoordinateFor(coordinates, direction);
            }
        }

        return "" + coordinates.getX() + ":" + coordinates.getY() + ":" + direction.getValue();
    }
}

