import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class NumberSystemLabel extends Label {
    private final NumberSystem numberSystem;

    public NumberSystemLabel(NumberSystem numberSystem) {
        super("0");
        setId("number-system-label");
        this.numberSystem = numberSystem;
        setWrapText(true);
        setTextAlignment(TextAlignment.LEFT);
        setMaxWidth(250);
    }

    public NumberSystem getNumberSystem() {
        return numberSystem;
    }
}
