public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate increaseX(int max) {
        return new Coordinate((x + 1) % max, y);
    }

    public Coordinate decreaseX(int max) {
        return new Coordinate(x > 0 ? x - 1 : max - 1, y);
    }

    public Coordinate increaseY(int max) {
        return new Coordinate(x, (y + 1) % max);
    }

    public Coordinate decreaseY(int max) {
        return new Coordinate(x, y == 0 ? max - 1 : (y - 1));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate stayInPlace() {
        return new Coordinate(x, y);
    }
}
