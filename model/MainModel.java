public class MainModel {
    private final Expression currentExpression = new Expression();
    private NumberSystem currentNumberSystem = NumberSystem.DEC; // value when program starts
    private Word currentWord = Word.QWORD; // value when program starts

    public MainModel() {
    }

    public void setCurrentNumberSystem(NumberSystem currentNumberSystem) {
        this.currentNumberSystem = currentNumberSystem;
    }

    public void setCurrentWord(Word currentWord) {
        this.currentWord = currentWord;
    }

    public Expression getCurrentExpression() {
        return currentExpression;
    }

    public NumberSystem getCurrentNumberSystem() {
        return currentNumberSystem;
    }

    public Word getCurrentWord() {
        return currentWord;
    }
}
