package v2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class AmountAndDate {
    public OperationType operationType;
    public int amount;
    public String date;

    public AmountAndDate(int amount, String date) {
        this.amount = abs(amount);
        this.date = date;
        this.operationType = amount > 0 ? OperationType.DEPOSIT : OperationType.WITHDRAW;
    }

    public static List<AmountAndDate> fromParam(String param) {
        String[] data = param.split("#");
        return Arrays.stream(data).map(amountAndDate -> {
            String[] values = amountAndDate.split("%");
            return new AmountAndDate(Integer.parseInt(values[0]), values[1]);
        }).collect(Collectors.toList());
    }
}
