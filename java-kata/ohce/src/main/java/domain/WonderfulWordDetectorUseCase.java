package domain;

public class WonderfulWordDetectorUseCase {
    public String execute(String wordSentByUser) {
        if (isPalindrome(wordSentByUser))
            return "¡Bonita palabra!";
        return "";
    }

    private boolean isPalindrome(String wordSentByUser) {
        return new StringBuilder(wordSentByUser).reverse().toString().equals(wordSentByUser);
    }
}
