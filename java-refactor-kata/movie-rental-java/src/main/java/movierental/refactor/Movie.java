package movierental.refactor;

public interface Movie {
    double amountOwedByRentalDaysDuration(int rentalDaysDuration);

    int frequentRenterPointsByDaysDuration(int rentalDaysDuration);

    String getTitle();
}
