package movierental.legacy;

/**
 * The rental class represents a customer renting a movie.
 */
public class RentalLegacy {

    private MovieLegacy _movie;
    private int _daysRented;

    public RentalLegacy(MovieLegacy movie, int daysRented) {
        _movie = movie;
        _daysRented = daysRented;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public MovieLegacy getMovie() {
        return _movie;
    }
}
