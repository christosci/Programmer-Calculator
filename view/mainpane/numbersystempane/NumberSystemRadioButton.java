import javafx.scene.control.RadioButton;

public final class NumberSystemRadioButton extends RadioButton {
    private final NumberSystem numberSystem;

    public NumberSystemRadioButton(String name, NumberSystem numberSystem) {
        super(name);
        this.numberSystem = numberSystem;
        getStyleClass().remove("radio-button");
        setId("number-system-radio-button");
    }

    public NumberSystem getNumberSystem() {
        return numberSystem;
    }
}
