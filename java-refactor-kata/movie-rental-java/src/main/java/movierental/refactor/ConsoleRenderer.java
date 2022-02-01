package movierental.refactor;

public class ConsoleRenderer implements Renderer {
    @Override
    public String renderHeader(String customerName) {
        return "Rental Record for " + customerName + "\n";
    }

    @Override
    public String renderBody(String movieTitle, double amountOwed) {
        return "\t" + movieTitle + "\t" + amountOwed + "\n";
    }

    @Override
    public String renderFooter(double totalAmountOwedByCustomer, int totalFrequentRenterPointsEarnedByCustomer) {
        return "Amount owed is " +
                totalAmountOwedByCustomer +
                "\n" +
                "You earned " +
                totalFrequentRenterPointsEarnedByCustomer +
                " frequent renter points";
    }
}
