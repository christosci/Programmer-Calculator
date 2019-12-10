/**
 * The CalculatorController class is a utility class that assists other controller classes.
 * It keeps information about the current state of the calculator, mainly concerning the view,
 * and handles special cases.
 */
public class CalculatorController {
    private final MainModel model;
    private final Expression currentExpression;

    private final FullKeypadPane fullKeypadPane;
    private final NumberSystemLabelController numberSystemLabelController;
    private final InputTextField inputTextField;
    private final InputHistoryLabel inputHistoryLabel;

    private boolean showingResult = false;
    private boolean showingError = false; // zero division is showing
    private boolean operatorButtonClicked = false;
    private Number lastOperand;
    private Operation lastOperator;


    public CalculatorController(MainModel model, EventController eventController) {
        this.model = model;
        currentExpression = model.getCurrentExpression();
        fullKeypadPane = eventController.getFullKeypadPane();
        inputTextField = eventController.getInputTextField();
        inputHistoryLabel = eventController.getInputHistoryLabel();
        numberSystemLabelController = eventController.getNumberSystemLabelController();
    }

    /**
     * Updates the view as appropriate to display the result of an expression,
     * or relevant error, if thrown.
     */
    public void showResult() {
        try {
            Number newOperand = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
            currentExpression.addOperand(newOperand);
            if (showingResult) {
                currentExpression.addOperator(lastOperator);
                currentExpression.addOperand(lastOperand);
            }
            else {
                lastOperand = newOperand;
            }
            Number result = model.getCurrentExpression().getResult();
            String newNumber = result.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord());
            inputTextField.setText(newNumber);
            inputTextField.setNumber(newNumber);
            numberSystemLabelController.updateNumber();
            inputHistoryLabel.clear();
            currentExpression.clear();
        }
        catch (ArithmeticException ae) {
            inputTextField.setText(ae.getMessage());
            disableOperatorButtons(true);
            showingError = true;
            numberSystemLabelController.setEmpty();
            inputHistoryLabel.clear();
        }
        showingResult = true;
    }

    /**
     * Updates the view as appropriate to display the result of an expression within
     * the last closed parenthesis.
     */
    public void showParenthesisResult() {
        try {
            Number result = model.getCurrentExpression().getLastParenthesisResult();
            String newNumber = result.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord());
            inputTextField.setText(newNumber);
            inputTextField.setNumber(newNumber);
            numberSystemLabelController.updateNumber();
        }
        catch (ArithmeticException ae) {
            inputTextField.setText(ae.getMessage());
            disableOperatorButtons(true);
            showingError = true;
            numberSystemLabelController.setEmpty();
            inputHistoryLabel.clear();
        }
    }

    /**
     * A utility method for operator button clicks.
     * @param operator The operator clicked.
     */
    public void operatorButton(Operation operator) {
        Number operand = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
        currentExpression.addOperand(operand);

        Number result = model.getCurrentExpression().getResult();
        String newNumber = result.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord());
        inputTextField.setText(newNumber);

        currentExpression.addOperator(operator);
        updateInputHistory();
        operatorButtonClicked = true;
        lastOperator = operator;
    }

    /**
     * A utility method for number and letter (hex) button clicks.
     * @param s The button's number or letter.
     */
    public void addNumberLetter(String s) {
        resetCalculator();
        if (showingResult) {
            inputTextField.clear();
            showingResult = false;
        }
        if (operatorButtonClicked) {
            inputTextField.clear();
            operatorButtonClicked = false;
        }
        if (Number.isWithinRange(inputTextField.getNumber() + s,
                model.getCurrentNumberSystem(), model.getCurrentWord())) {
            inputTextField.append(s);
            numberSystemLabelController.updateNumber();
        }
    }

    /**
     * Updates the input history label with the expression stored in the Model.
     */
    public void updateInputHistory() {
        String newInputHistory = currentExpression.toString(model.getCurrentNumberSystem(), model.getCurrentWord());
        inputHistoryLabel.setText(newInputHistory);
    }

    public void updateInputTextField() {
        Number newNumber = new Number(inputTextField.getNumber(), model.getCurrentNumberSystem());
        inputTextField.setText(newNumber.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord()));
        inputTextField.setNumber(newNumber.getNumber(model.getCurrentNumberSystem(), model.getCurrentWord()));
    }

    /**
     * If an error is being shown, this method resets the calculator to its starting stage.
     */
    public void resetCalculator() {
        if (showingError) {
            disableOperatorButtons(false);
            inputHistoryLabel.clear();
            inputTextField.clear();
            showingError = false;
        }
    }

    /**
     * Disables all relevant operator buttons. This includes all buttons except for NumberButtons,
     * LetterButtons, the equals button, backspace, and clear buttons.
     * @param disable Defines whether or not the relevant operator buttons should be disabled.
     */
    private void disableOperatorButtons(boolean disable) {
        fullKeypadPane.getOperationButtons().forEach(operationButton -> operationButton.setDisable(disable));
        fullKeypadPane.getMiscButtons().forEach(miscButton -> miscButton.setDisable(disable));
        fullKeypadPane.getParenthesisButtons().forEach(parenthesisButton -> parenthesisButton.setDisable(disable));
        fullKeypadPane.getRshButton().setDisable(disable);
        fullKeypadPane.getLshButton().setDisable(disable);
        fullKeypadPane.getShiftButton().setDisable(disable);
        fullKeypadPane.getNegateButton().setDisable(disable);
    }
}
