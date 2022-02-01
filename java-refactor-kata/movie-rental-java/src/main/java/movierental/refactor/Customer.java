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

        _totalAmountOwedByCustomer += rental.amountOwedForRental();
        _totalFrequentRenterPointsEarnedByCustomer += rental.frequentRenterPointsEarnedForRental();

        _rentals.add(rental);
    }

    public String statement(Renderer renderer) {
        return renderer.renderHeader(_name) +
                statementBody(renderer) +
                renderer.renderFooter(_totalAmountOwedByCustomer, _totalFrequentRenterPointsEarnedByCustomer);
    }

    private String statementBody(Renderer renderer) {
        return _rentals.stream().map(rental -> renderer.renderBody(rental.movieTitleRented(),
                rental.amountOwedForRental())).collect(Collectors.joining());
    }
}
