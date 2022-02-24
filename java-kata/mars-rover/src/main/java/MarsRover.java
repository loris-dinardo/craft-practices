public class MarsRover {
    private String direction;

    public MarsRover(String facingDirection) {
        this.direction = facingDirection;
    }

    public String execute(String[] commands) {
        for (String command : commands) {
            if (command.equals("R")) {
                moveRight();
            }
        }

        return "0:0:" + this.direction;
    }

    private void moveRight() {
        switch (this.direction) {
            case "N":
                this.direction = "E";
                break;
            case "E":
                this.direction = "S";
                break;
            case "S":
                this.direction = "W";
                break;
            case "W":
                this.direction = "N";
        }
    }
}
