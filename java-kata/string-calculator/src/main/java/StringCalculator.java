import java.security.InvalidParameterException;
import java.util.Arrays;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;
        this.checkValidParameters(numbers);
        return this.doSum(numbers);
    }

    private void checkValidParameters(String numbers) {
        if (numbers.endsWith(",") | numbers.endsWith("\n"))
            throw new InvalidParameterException();
    }

    private int doSum(String numbers) {
        this.findInvalidSeparators(this.findNumbers(numbers), this.findSeparators(numbers));
        return Arrays
                .stream(this.findNumbers(numbers)
                        .split(this.findSeparators(numbers)))
                .mapToInt(Integer::parseInt).sum();
    }

    private String findNumbers(String numbers) {
        if (numbers.startsWith("//")) {
            int index = numbers.indexOf("\n");
            return numbers.substring(index + 1);
        }
        return numbers;
    }

    private String findSeparators(String numbers) {
        if (numbers.startsWith("//")) {
            String delimiters = numbers.substring(2, numbers.indexOf("\n"));
            if (delimiters.equals("|")) return "\\|";
            return delimiters;
        }
        return ",|\n";
    }

    private void findInvalidSeparators(String numbers, String validSeparators) {
        if (!numbers.matches(validSeparators + "|[0-9]"))
            throw new InvalidSeparatorsException("Invalid separators found");
    }
}
