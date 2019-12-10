import javafx.scene.control.Button;

public class KeypadButton extends Button {
    public KeypadButton(String s) {
        super(s);
    }

    public KeypadButton(String s, String id) {
        this(s);
        setId(id);
    }
}
