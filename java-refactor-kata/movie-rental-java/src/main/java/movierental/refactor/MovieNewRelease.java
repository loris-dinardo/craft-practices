package movierental.refactor;

public class MovieNewRelease implements Movie {
    private final String title;

    public MovieNewRelease(String title) {
        this.title = title;
    }

    @Override
    public double amountOwedByRentalDaysDuration(int rentalDaysDuration) {
        return rentalDaysDuration * 3;
    }

    @Override
    public int frequentRenterPointsByDaysDuration(int rentalDaysDuration) {
        return rentalDaysDuration > 1
                ? 2
                : 1;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
