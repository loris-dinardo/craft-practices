package movierental;

import movierental.legacy.CustomerLegacy;
import movierental.legacy.MovieLegacy;
import movierental.legacy.RentalLegacy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CustomerOriginalTest {

    @Test
    public void test() {
        CustomerLegacy customer = new CustomerLegacy("Bob");
        customer.addRental(new RentalLegacy(new MovieLegacy("Jaws", MovieLegacy.REGULAR), 2));
        customer.addRental(new RentalLegacy(new MovieLegacy("Golden Eye", MovieLegacy.REGULAR), 3));
        customer.addRental(new RentalLegacy(new MovieLegacy("Short New", MovieLegacy.NEW_RELEASE), 1));
        customer.addRental(new RentalLegacy(new MovieLegacy("Long New", MovieLegacy.NEW_RELEASE), 2));
        customer.addRental(new RentalLegacy(new MovieLegacy("Bambi", MovieLegacy.CHILDRENS), 3));
        customer.addRental(new RentalLegacy(new MovieLegacy("Toy Story", MovieLegacy.CHILDRENS), 4));

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