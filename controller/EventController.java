import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The EventController handles events from the View, updates the Model,
 * and then updates the View if necessary.
 */
public final class EventController {
    // view
    private final Scene scene;
    private final MainPane mainPane;
    private final NumberSystemPane numberSystemPane;
    private final Button wordButton;
    private final FullKeypadPane fullKeypadPane;
    private final InputHistoryLabel inputHistoryLabel;
    private final InputTextField inputTextField;
    // Model
    private final MainModel model;
    private final Expression currentExpression;
    // Controller
    private NumberSystemLabelController numberSystemLabelController;
    private CalculatorController calculatorController;

    public EventController(MainModel model, MainView view) {
        scene             = view.getScene();
        mainPane          = view.getMainPane();
        numberSystemPane  = mainPane.getNumberSystemPane();
        wordButton        = mainPane.getWordButton();
        fullKeypadPane    = mainPane.getKeypadTabPane().getFullKeypadPane();
        inputHistoryLabel = mainPane.getInputHistoryLabel();
        inputTextField    = mainPane.getInputTextField();

        this.model          = model;
        currentExpression   = model.getCurrentExpression();
    }

    /**
     * Sets EventHandlers and Listeners for every view component.
     */
    public void init() {
        numberSystemLabelController = new NumberSystemLabelController(model, this);
        calculatorController = new CalculatorController(model, this);
        numberSystemPane.getNumberSystemToggleGroup().selectedToggleProperty().
                addListener(new NumberSystemController(model, this));
        wordButton.setOnAction(this::wordButtonClick);

        fullKeypadPane.getClearEntryButton().setOnAction(this::clearButtonClick);
        fullKeypadPane.getClearButton().setOnAction(this::clearButtonClick);
        fullKeypadPane.getNumberButtons().forEach(btn -> btn.setOnAction(this::numberLetterButtonClick));
        fullKeypadPane.getLetterButtons().forEach(btn -> btn.setOnAction(this::numberLetterButtonClick));
        fullKeypadPane.getOperationButtons().forEach(btn -> btn.setOnAction(this::operationButtonClick));
        fullKeypadPane.getEqualsButton().setOnAction(this::equalsButtonClick);
        fullKeypadPane.getShiftButton().setOnAction(this::shiftButtonClick);
        fullKeypadPane.getParenthesisButtons().forEach(btn -> btn.setOnAction(this::parenthesisButtonClick));
        fullKeypadPane.getBackButton().setOnAction(this::backButtonClick);
        fullKeypadPane.getNegateButton().setOnAction(this::negateButtonClick);

        // Handle keyboard events
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
        scene.addEventFilter(KeyEvent.KEY_TYPED, this::keyTyped);
    }

    /**
     * Handle KEY_TYPED events. All keys except enter and backspace.
     * @param e KeyEvent
     */
    private void keyTyped(KeyEvent e) {
        String character = e.getCharacter();
        for (NumberButton btn : fullKeypadPane.getNumberButtons()) {
            if (character.equals(btn.getText())) {
                btn.fire();
                return;
            }
        }
        for (LetterButton btn : fullKeypadPane.getLetterButtons()) {
            if (character.toUpperCase().equals(btn.getText())) {
                btn.fire();
                return;
            }
        }
        for (OperationButton btn : fullKeypadPane.getOperationButtons()) {
            if (btn.getOperation().getMachineReadableSymbol().equals(character)) {
                btn.fire();
                return;
            }
        }
        for (KeypadButton btn : fullKeypadPane.getParenthesisButtons()) {
            if (character.equals(btn.getText())) {
                btn.fire();
                return;
            }
        }
    }

    /**
     * Handle KEY_PRESSED events. Backspace and Enter.
     * @param e KeyEvent
     */
    private void keyPressed(KeyEvent e) {
        // Focus on mainPane so that ENTER doesn't also fire the last clicked button.
        mainPane.requestFocus();
        KeyCode code = e.getCode();
        if (code == KeyCode.BACK_SPACE)
            fullKeypadPane.getBackButton().fire();
        else if (code == KeyCode.ENTER)
            fullKeypadPane.getEqualsButton().fire();
    }

    /**
     * Handles the WORD (binary size) button.
     * @param e ActionEvent
     */
    private void wordButtonClick(ActionEvent e) {
        Word newWord = model.getCurrentWord().next();
        wordButton.setText(newWord.toString());
        model.setCurrentWord(newWord);
        numberSystemLabelController.updateNumber();
        calculatorController.updateInputHistory();
        calculatorController.updateInputTextField();
    }

    /**
     * Handles the Number and Letter (Hex) buttons.
     * @param e ActionEvent
     */
    private void numberLetterButtonClick(ActionEvent e) {
        KeypadButton button = (KeypadButton)e.getSource();
        calculatorController.addNumberLetter(button.getText());
    }

    /**
     * Handles the operatorButtons (add, subtract, divide, multiply, modulo).
     * @param e ActionEvent
     */
    private void operationButtonClick(ActionEvent e) {
        OperationButton operationButton = (OperationButton)e.getSource();
        calculatorController.operatorButton(operationButton.getOperation());
    }

    /**
     * Handles the equals button.
     * @param e ActionEvent
     */
    private void equalsButtonClick(ActionEvent e) {
        calculatorController.resetCalculator();
        calculatorController.showResult();
        currentExpression.clear();
    }

    /**
     * Handles the parenthesis button (both open and close).
     * @param e ActionEvent
     */
    private void parenthesisButtonClick(ActionEvent e) {
        KeypadButton parenthesisButton = (KeypadButton)e.getSource();
        String text = parenthesisButton.getText();
        if (text.equals("(")) {
            currentExpression.openParenthesis();
        }
        else { // close parenthesis, send new operand to Model.
            Number operand = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
            currentExpression.addOperand(operand);
            currentExpression.closeParenthesis();
            calculatorController.showParenthesisResult(); // get the result of the expression within the parenthesis.
        }
        inputHistoryLabel.setText(currentExpression.toString(model.getCurrentNumberSystem(), model.getCurrentWord()));
    }

    /**
     * Handles the Negate button.
     * @param e ActionEvent
     */
    private void negateButtonClick(ActionEvent e) {
        Number operand = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
        currentExpression.addOperand(operand);
        currentExpression.negateLast();

        Number negatedOperand = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
        negatedOperand.negate();
        inputTextField.setText(negatedOperand.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord()));
        inputTextField.setNumber(negatedOperand.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord()));

        String newInputHistory = model.getCurrentExpression().toString(model.getCurrentNumberSystem(), model.getCurrentWord());
        inputHistoryLabel.setText(newInputHistory);
        numberSystemLabelController.updateNumber();
    }

    /**
     * Handles the shift (up arrow) button. This only updates the view.
     * @param e ActionEvent
     */
    private void shiftButtonClick(ActionEvent e) {
        ToggleButton shiftButton = (ToggleButton)e.getSource();
        if (shiftButton.isSelected()) {
            fullKeypadPane.getLshButton().setText("RoL");
            fullKeypadPane.getRshButton().setText("RoR");
        }
        else {
            fullKeypadPane.getLshButton().setText("Lsh");
            fullKeypadPane.getRshButton().setText("Rsh");
        }
    }

    /**
     * Handles the clear (C) and clear entry (CE) buttons.
     * @param e ActionEvent
     */
    private void clearButtonClick(ActionEvent e) {
        KeypadButton button = (KeypadButton)e.getSource();
        calculatorController.resetCalculator();
        if (button.getId().equals("clear-button")) {
            inputHistoryLabel.clear();
            currentExpression.clear();
        }
        inputTextField.clear();
        numberSystemLabelController.clear();
    }

    /**
     * Handles the backspace button.
     * @param e ActionEvent
     */
    private void backButtonClick(ActionEvent e) {
        calculatorController.resetCalculator();
        inputTextField.backspace();
        numberSystemLabelController.updateNumber();
    }

    public CalculatorController getCalculatorController() {
        return calculatorController;
    }

    public NumberSystemPane getNumberSystemPane() {
        return numberSystemPane;
    }

    public FullKeypadPane getFullKeypadPane() {
        return fullKeypadPane;
    }

    public InputHistoryLabel getInputHistoryLabel() {
        return inputHistoryLabel;
    }

    public InputTextField getInputTextField() {
        return inputTextField;
    }

    public NumberSystemLabelController getNumberSystemLabelController() {
        return numberSystemLabelController;
    }
}
