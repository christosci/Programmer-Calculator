import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;

import java.util.ArrayList;

/**
 * This class updates the View and Model every time the number system radio buttons change state.
 */
public final class NumberSystemController implements ChangeListener<Toggle> {
    private final MainModel model;
    private final ArrayList<NumberButton> numberButtons;
    private final ArrayList<LetterButton> letterButtons;
    private final InputHistoryLabel inputHistoryLabel;
    private final InputTextField inputTextField;

    private NumberSystem previousNumberSystem;
    private NumberSystem newNumberSystem;
    private final CalculatorController calculatorController;

    public NumberSystemController(MainModel model, EventController eventController) {
        this.model        = model;
        numberButtons     = eventController.getFullKeypadPane().getNumberButtons();
        letterButtons     = eventController.getFullKeypadPane().getLetterButtons();
        inputHistoryLabel = eventController.getInputHistoryLabel();
        inputTextField    = eventController.getInputTextField();
        calculatorController  = eventController.getCalculatorController();
    }

    @Override
    public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) {
        previousNumberSystem = ((NumberSystemRadioButton)oldToggle).getNumberSystem();
        newNumberSystem      = ((NumberSystemRadioButton)newToggle).getNumberSystem();
        updateView();
    }

    /**
     * Updates the history and input text field to display numbers using the selected number system.
     * Disables or enables the appropriate keypad buttons.
     */
    private void updateView() {
        calculatorController.resetCalculator();
        String inputNumber = new Number(inputTextField.getNumber(), previousNumberSystem).getNumber(newNumberSystem, model.getCurrentWord());
        inputTextField.setText(inputNumber);
        inputTextField.setNumber(inputNumber);
        inputHistoryLabel.setText(model.getCurrentExpression().toString(newNumberSystem, model.getCurrentWord()));

        model.setCurrentNumberSystem(newNumberSystem);
        switch (newNumberSystem) {
            case HEX:
                enableHexadecimal();
                break;
            case DEC:
                enableDecimal();
                break;
            case OCT:
                enableOctal();
                break;
            case BIN:
                enableBinary();
                break;
        }
    }

    private void enableHexadecimal() {
        disableLetterButtons(false);
        disableNumberButtons(false);
    }

    private void enableDecimal() {
        disableLetterButtons(true);
        disableNumberButtons(false);
    }

    private void enableOctal() {
        disableLetterButtons(true);
        for (NumberButton nb : numberButtons) {
            if (nb.getNumber() == 8 || nb.getNumber() == 9)
                nb.setDisable(true);
            else
                nb.setDisable(false);
        }
    }

    private void enableBinary() {
        disableLetterButtons(true);
        for (NumberButton nb : numberButtons) {
            if (nb.getNumber() == 0 || nb.getNumber() == 1)
                nb.setDisable(false);
            else
                nb.setDisable(true);
        }
    }

    private void disableLetterButtons(boolean disable) {
        for (LetterButton lb : letterButtons)
            lb.setDisable(disable);
    }

    private void disableNumberButtons(boolean disable) {
        for (NumberButton nb : numberButtons)
            nb.setDisable(disable);
    }
}
