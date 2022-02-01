package movierental.refactor;

public interface Renderer {
    String renderHeader(String customerName);

    String renderBody(String movieTitle, double amountOwed);

    String renderFooter(double totalAmountOwedByCustomer, int totalFrequentRenterPointsEarnedByCustomer);
}
