public class ConsoleOutputGenerationBoard implements OutputGenerationBoard {
    private String lastPrinted = "";

    @Override
    public void outputBoard(Cell[][] worldBoard) {
        StringBuilder displayBoard = new StringBuilder();
        displayBoard.append("[");
        for (int i = 0; i < worldBoard.length; i++) {
            for (int j = 0; j < worldBoard[i].length; j++) {
                displayBoard.append(worldBoard[i][j].getState());
                displayBoard.append(j < worldBoard[i].length - 1 ? " " : "");
            }
            displayBoard.append(i < worldBoard.length - 1 ? "\n" : "");
        }
        displayBoard.append("]");
        lastPrinted = displayBoard.toString();
    }

    public String printed() {
        return lastPrinted;
    }
}
