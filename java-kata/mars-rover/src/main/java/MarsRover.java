public class MarsRover {
    private Direction direction;

    public MarsRover(Direction facingDirection) {
        this.direction = facingDirection;
    }

    public String execute(String[] commands) {
        for (String command : commands) {
            if (command.equals("R")) {
                this.direction = this.direction.turnRight();
            }
            if (command.equals("L")) {
                this.direction = this.direction.turnLeft();
            }
        }

        return "0:0:" + this.direction.getValue();
    }
}

