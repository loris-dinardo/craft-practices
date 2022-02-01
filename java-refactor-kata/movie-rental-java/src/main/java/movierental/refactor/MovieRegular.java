package movierental.refactor;

public class MovieRegular implements Movie {
    private final String title;

    public MovieRegular(String title) {
        this.title = title;
    }

    @Override
    public double amountOwedByRentalDaysDuration(int rentalDaysDuration) {
        return (rentalDaysDuration > 2)
                ? 2 + (rentalDaysDuration - 2) * 1.5
                : 2;
    }

    @Override
    public int frequentRenterPointsByDaysDuration(int rentalDaysDuration) {
        return 1;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
