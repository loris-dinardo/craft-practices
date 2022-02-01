package movierental.refactor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {

    private final String _name;
    private final List<Rental> _rentals;
    private double _totalAmountOwedByCustomer;
    private int _totalFrequentRenterPointsEarnedByCustomer;

    public Customer(String name) {
        _name = name;
        _rentals = new ArrayList<>();
    }

    public void addRental(Movie movie, int rentalDayDuration) {
        Rental rental = new Rental(movie, rentalDayDuration);

        _totalAmountOwedByCustomer += rental.amountOwedByRental();
        _totalFrequentRenterPointsEarnedByCustomer += rental.getFrequentRenterPoints();

        _rentals.add(rental);
    }

    public String statement() {
        return statementHeader() + statementBody() + statementFooter();
    }

    private String statementHeader() {
        return "Rental Record for " + this._name + "\n";
    }

    private String statementBody() {
        return _rentals.stream().map(rental -> "\t" + rental.displayFigures() + "\n").collect(Collectors.joining());
    }

    private String statementFooter() {
        return "Amount owed is " + _totalAmountOwedByCustomer + "\n" +
                "You earned " + _totalFrequentRenterPointsEarnedByCustomer + " frequent renter points";
    }
}
