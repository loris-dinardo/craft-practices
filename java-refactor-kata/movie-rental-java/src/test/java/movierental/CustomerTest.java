package movierental;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CustomerTest {

    @Test
    public void test() {
        CustomerLegacy customer = new CustomerLegacy("Bob");
        customer.addRental(new RentalLegacy(new MovieLegacy("Jaws", Movie.REGULAR), 2));
        customer.addRental(new RentalLegacy(new MovieLegacy("Golden Eye", Movie.REGULAR), 3));
        customer.addRental(new RentalLegacy(new MovieLegacy("Short New", Movie.NEW_RELEASE), 1));
        customer.addRental(new RentalLegacy(new MovieLegacy("Long New", Movie.NEW_RELEASE), 2));
        customer.addRental(new RentalLegacy(new MovieLegacy("Bambi", Movie.CHILDRENS), 3));
        customer.addRental(new RentalLegacy(new MovieLegacy("Toy Story", Movie.CHILDRENS), 4));

        String expected = "" +
                "Rental Record for Bob\n" +
                "\tJaws\t2.0\n" +
                "\tGolden Eye\t3.5\n" +
                "\tShort New\t3.0\n" +
                "\tLong New\t6.0\n" +
                "\tBambi\t1.5\n" +
                "\tToy Story\t3.0\n" +
                "Amount owed is 19.0\n" +
                "You earned 7 frequent renter points";

        assertEquals(expected, customer.statement());
    }
}