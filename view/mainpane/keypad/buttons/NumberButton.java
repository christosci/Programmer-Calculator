public class NumberButton extends KeypadButton {

    public NumberButton(int i) {
        super(String.valueOf(i));
        setId("number-button");
    }

    public int getNumber() {
        return Integer.valueOf(getText());
    }
}
