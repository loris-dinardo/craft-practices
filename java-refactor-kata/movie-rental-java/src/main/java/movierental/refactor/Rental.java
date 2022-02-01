package movierental.refactor;

/**
 * The rental class represents a customer renting a movie.
 */
public class Rental {
    private final double _amountOwedByCustomer;
    private final int _frequentRenterPointsEarnedByCustomer;
    private final String _figuresToDisplay;

    public Rental(Movie movie, int daysRented) {
        _amountOwedByCustomer = movie.amountOwedByRentalDaysDuration(daysRented);
        _frequentRenterPointsEarnedByCustomer = movie.frequentRenterPointsByDaysDuration(daysRented);
        _figuresToDisplay = movie.getTitle() + "\t" + _amountOwedByCustomer;
    }

    public double amountOwedByRental() {
        return this._amountOwedByCustomer;
    }

    public int getFrequentRenterPoints() {
        return this._frequentRenterPointsEarnedByCustomer;
    }

    public String displayFigures() {
        return this._figuresToDisplay;
    }
}
