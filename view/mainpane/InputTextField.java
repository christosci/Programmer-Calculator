import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class InputTextField extends TextField {
    private String number = "0";

    public InputTextField() {
        super("0"); // initial value
        setAlignment(Pos.CENTER_RIGHT);
        setEditable(false);
    }

    public void append(String s) {
        if (isEmpty()) {
            setText(s);
            number = s;
        }
        else {
            setText(getText() + s);
            number += s;
        }
    }

    public void backspace() {
        if (getLength() != 1) {
            setText(getText().substring(0, getText().length() - 1));
            number = number.substring(0, number.length() - 1);
        }
        else {
            clear();
        }
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void clear() {
        setText("0");
        number = "0";
    }

    public boolean isEmpty() {
        return getText().equals("0");
    }
}
