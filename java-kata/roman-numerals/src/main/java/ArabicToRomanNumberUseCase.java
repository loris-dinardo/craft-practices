public class ArabicToRomanNumberUseCase {
    private enum Roman {
        M(1000),
        CM(900),
        D(500),
        CD(400),
        C(100),
        XC(90),
        L(50),
        XL(40),
        X(10),
        IX(9),
        V(5),
        IV(4),
        I(1);

        private final int value;

        Roman(int value) {
            this.value = value;
        }
    }

    public String execute(int arabicNumber) {
        for (Roman roman : Roman.values()) {
            if (arabicNumber >= roman.value)
                return roman.name() + execute(arabicNumber - roman.value);
        }
        return "";
    }
}
