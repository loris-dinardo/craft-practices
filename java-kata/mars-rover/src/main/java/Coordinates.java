import java.util.Objects;

public class Coordinates {

    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates increaseX(int max) {
        return new Coordinates((x + 1) % max, y);
    }

    public Coordinates decreaseX(int max) {
        return new Coordinates(x > 0 ? x - 1 : max - 1, y);
    }

    public Coordinates increaseY(int max) {
        return new Coordinates(x, (y + 1) % max);
    }

    public Coordinates decreaseY(int max) {
        return new Coordinates(x, y == 0 ? max - 1 : (y - 1));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates stayInPlace() {
        return new Coordinates(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
