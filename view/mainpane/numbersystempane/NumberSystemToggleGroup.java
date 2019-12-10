import javafx.scene.control.ToggleGroup;

public final class NumberSystemToggleGroup extends ToggleGroup {
    private final NumberSystemRadioButton hexRadioButton, decRadioButton, octRadioButton, binRadioButton;

    public NumberSystemToggleGroup() {
        super();
        hexRadioButton = new NumberSystemRadioButton("HEX", NumberSystem.HEX);
        decRadioButton = new NumberSystemRadioButton("DEC", NumberSystem.DEC);
        octRadioButton = new NumberSystemRadioButton("OCT", NumberSystem.OCT);
        binRadioButton = new NumberSystemRadioButton("BIN", NumberSystem.BIN);

        hexRadioButton.setToggleGroup(this);
        decRadioButton.setToggleGroup(this);
        octRadioButton.setToggleGroup(this);
        binRadioButton.setToggleGroup(this);

        decRadioButton.setSelected(true);
    }

    public NumberSystemRadioButton getHexRadioButton() {
        return hexRadioButton;
    }

    public NumberSystemRadioButton getDecRadioButton() {
        return decRadioButton;
    }

    public NumberSystemRadioButton getOctRadioButton() {
        return octRadioButton;
    }

    public NumberSystemRadioButton getBinRadioButton() {
        return binRadioButton;
    }
}
