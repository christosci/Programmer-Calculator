import java.util.ArrayList;

/**
 * This class updates the number system labels to display the correct number for every base
 * and binary size.
 */
public class NumberSystemLabelController {
    private final MainModel model;
    private final InputTextField inputTextField;
    private final ArrayList<NumberSystemLabel> numberSystemLabels;

    public NumberSystemLabelController(MainModel model, EventController eventController) {
        this.model = model;
        this.inputTextField = eventController.getInputTextField();
        this.numberSystemLabels = eventController.getNumberSystemPane().getNumberSystemLabels();
    }

    /**
     * Updates the number system labels.
     */
    public void updateNumber() {
        for (NumberSystemLabel label : numberSystemLabels) {
            Number newNumber = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
            label.setText(newNumber.getFormattedNumber(label.getNumberSystem(), model.getCurrentWord()));
        }
    }

    /**
     * Clears the number System labels (resets to 0).
     */
    public void clear() {
        for (NumberSystemLabel label : numberSystemLabels) {
            label.setText("0");
        }
    }

    /**
     * Completely clears the number System labels (sets to empty string).
     */
    public void setEmpty() {
        for (NumberSystemLabel label : numberSystemLabels) {
            label.setText("");
        }
    }
}
