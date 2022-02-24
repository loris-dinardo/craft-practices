public enum Direction {
    NORTH("N", "W", "E"),
    EAST("E", "N", "S"),
    SOUTH("S", "E", "W"),
    WEST("W", "S", "N");

    private final String value;
    private final String left;
    private final String right;

    Direction(String value, String left, String right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public Direction turnLeft() {
        return directionMatching(this.left);
    }

    public Direction turnRight() {
        return directionMatching(this.right);
    }

    private Direction directionMatching(String value) {
        for (Direction direction : values()) {
            if (direction.value.equals(value))
                return direction;
        }
        return this;
    }
}
