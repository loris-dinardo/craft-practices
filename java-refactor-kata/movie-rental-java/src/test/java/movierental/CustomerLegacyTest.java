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

        assertEquals(customerLegacy.statement(), customer.statement(new ConsoleRenderer()));
    }

    @Test
    public void refactoredCodeMustDisplayStatementInHtml() {
        String expected =
                "" +
                        "<h1>Rental Record for Bob</h1>\n" +
                        "<table>" +
                        "<tr><td>Jaws</td><td>2.0</td></tr>\n" +
                        "<tr><td>Golden Eye</td><td>3.5</td></tr>\n" +
                        "<tr><td>Short New</td><td>3.0</td></tr>\n" +
                        "<tr><td>Long New</td><td>6.0</td></tr>\n" +
                        "<tr><td>Bambi</td><td>1.5</td></tr>\n" +
                        "<tr><td>Toy Story</td><td>3.0</td></tr>\n" +
                        "</table>" +
                        "<p>Amount owed is <em>19.0</em></p>\n" +
                        "<p>You earned <em>7</em> frequent renter points</p>";

        Customer customer = new Customer("Bob");
        for (TestRental rental : rentals) {
            customer.addRental(getMovieByPriceCode(rental.movieTitle, rental.moviePriceCode), rental.daysRented);
        }

        assertEquals(expected, customer.statement(new HtmlRenderer()));
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
