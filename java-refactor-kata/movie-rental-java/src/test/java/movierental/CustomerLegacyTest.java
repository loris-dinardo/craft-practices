package movierental;

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
            new TestRental("Jaws", Movie.REGULAR, 2),
            new TestRental("Golden Eye", Movie.REGULAR, 3),
            new TestRental("Short New", Movie.NEW_RELEASE, 1),
            new TestRental("Long New", Movie.NEW_RELEASE, 2),
            new TestRental("Bambi", Movie.CHILDRENS, 3),
            new TestRental("Toy Story", Movie.CHILDRENS, 4)
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
        customerLegacy.addRental(new RentalLegacy(new MovieLegacy(rental.movieTitle, rental.moviePriceCode), rental.daysRented));
        customer.addRental(new Rental(new Movie(rental.movieTitle, rental.moviePriceCode), rental.daysRented));
    }
}
