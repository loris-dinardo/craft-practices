package movierental.refactor;

public class MovieForChildren implements Movie {
    private final String title;

    public MovieForChildren(String title) {
        this.title = title;
    }

    @Override
    public double amountOwedByRentalDaysDuration(int rentalDaysDuration) {
        return rentalDaysDuration > 3
                ? 1.5 + (rentalDaysDuration - 3) * 1.5
                : 1.5;
    }

    @Override
    public int frequentRenterPointsByDaysDuration(int rentalDaysDuration) {
        return 1;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
