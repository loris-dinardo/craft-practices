package movierental.refactor;

public class HtmlRenderer implements Renderer {
    @Override
    public String renderHeader(String customerName) {
        return "<h1>Rental Record for " + customerName + "</h1>\n<table>";
    }

    @Override
    public String renderBody(String movieTitle, double amountOwed) {
        return "<tr><td>" + movieTitle + "</td><td>" + amountOwed + "</td></tr>\n";
    }

    @Override
    public String renderFooter(double totalAmountOwedByCustomer, int totalFrequentRenterPointsEarnedByCustomer) {
        return "</table><p>Amount owed is <em>" +
                totalAmountOwedByCustomer +
                "</em></p>\n" +
                "<p>You earned <em>" +
                totalFrequentRenterPointsEarnedByCustomer +
                "</em> frequent renter points</p>";
    }
}
