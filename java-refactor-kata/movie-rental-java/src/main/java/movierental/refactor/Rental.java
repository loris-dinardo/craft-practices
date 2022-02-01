package movierental.refactor;

/**
 * The rental class represents a customer renting a movie.
 */
public class Rental {
    private final double _amountOwedForRental;
    private final int _frequentRenterPointsEarnedForRental;
    private final String _movieTitleRented;

    public Rental(Movie movie, int daysRented) {
        _amountOwedForRental = movie.amountOwedByRentalDaysDuration(daysRented);
        _frequentRenterPointsEarnedForRental = movie.frequentRenterPointsByDaysDuration(daysRented);
        _movieTitleRented = movie.getTitle();
    }

    public double amountOwedForRental() {
        return this._amountOwedForRental;
    }

    public int frequentRenterPointsEarnedForRental() {
        return this._frequentRenterPointsEarnedForRental;
    }

    public String movieTitleRented() {
        return this._movieTitleRented;
    }
}
