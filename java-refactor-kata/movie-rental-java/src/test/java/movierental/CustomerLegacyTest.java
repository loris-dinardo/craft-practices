package movierental;

import movierental.legacy.CustomerLegacy;
import movierental.legacy.MovieLegacy;
import movierental.legacy.RentalLegacy;
import movierental.refactor.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerLegacyTest {
    static class TestRental {
        public TestRental(String movieTitle, Integer moviePriceCode, Integer daysRented) {
            this.movieTitle = movieTitle;
            this.moviePriceCode = moviePriceCode;
            this.daysRented = daysRented;
        }

        String movieTitle;
        Integer moviePriceCode;
        Integer daysRented;
    }

    List<TestRental> rentals = List.of(
            new TestRental("Jaws", MovieLegacy.REGULAR, 2),
            new TestRental("Golden Eye", MovieLegacy.REGULAR, 3),
            new TestRental("Short New", MovieLegacy.NEW_RELEASE, 1),
            new TestRental("Long New", MovieLegacy.NEW_RELEASE, 2),
            new TestRental("Bambi", MovieLegacy.CHILDRENS, 3),
            new TestRental("Toy Story", MovieLegacy.CHILDRENS, 4)
    );

    @Test
    public void preventRegressionFromLegacy() {
        Customer customer = new Customer("Bob");
        CustomerLegacy customerLegacy = new CustomerLegacy("Bob");
        for (TestRental rental : rentals) {
            addRentalForCustomer(customerLegacy, customer, rental);
        }

        assertEquals(customerLegacy.statement(), customer.statement());
    }

    private void addRentalForCustomer(CustomerLegacy customerLegacy,
                                      Customer customer,
                                      TestRental rental) {
        customerLegacy.addRental(new RentalLegacy(new MovieLegacy(rental.movieTitle, rental.moviePriceCode),
                rental.daysRented));

        customer.addRental(getMovieByPriceCode(rental.movieTitle, rental.moviePriceCode),
                rental.daysRented);
    }

    private Movie getMovieByPriceCode(String title, int priceCode) {
        switch (priceCode) {
            default:
                return new MovieRegular(title);
            case MovieLegacy.NEW_RELEASE:
                return new MovieNewRelease(title);
            case MovieLegacy.CHILDRENS:
                return new MovieForChildren(title);
        }
    }
}
