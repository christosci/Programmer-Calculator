public class LetterButton extends KeypadButton {
    public LetterButton(char c) {
        super(String.valueOf(c));
        setDisable(true); // initial state
        setId("letter-button");
    }
}
