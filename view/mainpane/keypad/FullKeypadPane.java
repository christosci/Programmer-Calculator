import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

public final class FullKeypadPane extends GridPane {
    private final ArrayList<LetterButton> letterButtons       = new ArrayList<>();
    private final ArrayList<NumberButton> numberButtons       = new ArrayList<>();
    private final ArrayList<OperationButton> operationButtons = new ArrayList<>();
    private final ArrayList<KeypadButton> parenthesisButtons  = new ArrayList<>();
    private final ArrayList<KeypadButton> miscButtons         = new ArrayList<>();
    private ToggleButton shiftButton;
    private KeypadButton equalsButton, clearEntryButton, clearButton, backButton,
            negateButton;
    private KeypadButton lshButton, rshButton;

    public FullKeypadPane() {
        super();
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(5));

        init();
    }

    private void init() {
        addDisplayOnlyButtons();
        addMiscButtons();
        addLetterButtons();
        addNumberButtons();
        addArithmeticOperatorButtons();
    }

    private void addMiscButtons() {
        shiftButton      = new ToggleButton("↑");
        clearEntryButton = new KeypadButton("CE", "clear-entry-button");
        clearButton      = new KeypadButton("C", "clear-button");
        backButton       = new KeypadButton("⌫", "back-button");
        equalsButton     = new KeypadButton("=");
        shiftButton.setId("shift-button");

        add(shiftButton, 0, 1);
        add(clearEntryButton, 2, 1);
        add(clearButton, 3, 1);
        add(backButton, 4, 1);

        negateButton = new KeypadButton("±", "plus-minus-button");
        add(negateButton, 2, 5);
        parenthesisButtons.add(new KeypadButton("(", "parenthesis-button"));
        parenthesisButtons.add(new KeypadButton(")", "parenthesis-button"));
        add(parenthesisButtons.get(0), 0, 5);
        add(parenthesisButtons.get(1), 1, 5);
    }

    private void addArithmeticOperatorButtons() {
        OperationButton divisionButton        = new OperationButton("÷", Operation.DIVIDE);
        OperationButton multiplicationButton  = new OperationButton("×", Operation.MULTIPLY);
        OperationButton subtractionButton     = new OperationButton("-", Operation.SUBTRACT);
        OperationButton additionButton        = new OperationButton("+", Operation.ADD);
        OperationButton modButton             = new OperationButton("Mod", Operation.MODULO);
        modButton.setId("modulo-button");

        operationButtons.addAll(Arrays.asList(
                divisionButton, multiplicationButton, subtractionButton, additionButton, modButton)
        );

        add(modButton, 1, 1);
        addColumn(5, divisionButton, multiplicationButton, subtractionButton, additionButton, equalsButton);
    }

    private void addLetterButtons() {
        char letter = 'A';
        for (int r=2; r<=4; r++) {
            for (int c=0; c<=1; c++) {
                LetterButton button = new LetterButton(letter);
                letterButtons.add(button);
                add(button, c, r);
                letter++;
            }
        }
    }

    private void addNumberButtons() {
        // add 0 button
        NumberButton button = new NumberButton(0);
        numberButtons.add(button);
        add(button, 3, 5);
        // add all other buttons
        int number = 1;
        for (int r=4; r>=2; r--) {
            for (int c=2; c<=4; c++) {
                button = new NumberButton(number);
                numberButtons.add(button);
                add(button, c, r);
                number++;
            }
        }
    }

    private void addDisplayOnlyButtons() {
        // bitwise operators
        this.lshButton           = new KeypadButton("Lsh");
        this.rshButton           = new KeypadButton("Rsh");
        KeypadButton orButton    = new KeypadButton("Or");
        KeypadButton xorButton   = new KeypadButton("Xor");
        KeypadButton notButton   = new KeypadButton("Not");
        KeypadButton andButton   = new KeypadButton("And");
        addRow(0, lshButton, rshButton, orButton, xorButton, notButton, andButton);
        miscButtons.addAll(Arrays.asList(orButton, xorButton, notButton, andButton));

        // decimal button - disabled by default
        KeypadButton decimalButton = new KeypadButton(".", "decimal-button");
        decimalButton.setDisable(true);
        add(decimalButton, 4, 5);
    }

    public ArrayList<LetterButton> getLetterButtons() {
        return letterButtons;
    }

    public ArrayList<NumberButton> getNumberButtons() {
        return numberButtons;
    }

    public ArrayList<OperationButton> getOperationButtons() {
        return operationButtons;
    }

    public ToggleButton getShiftButton() {
        return shiftButton;
    }

    public KeypadButton getEqualsButton() {
        return equalsButton;
    }

    public KeypadButton getClearEntryButton() {
        return clearEntryButton;
    }

    public KeypadButton getClearButton() {
        return clearButton;
    }

    public KeypadButton getBackButton() {
        return backButton;
    }

    public KeypadButton getNegateButton() {
        return negateButton;
    }

    public ArrayList<KeypadButton> getParenthesisButtons() {
        return parenthesisButtons;
    }

    public ArrayList<KeypadButton> getMiscButtons() {
        return miscButtons;
    }

    public KeypadButton getLshButton() {
        return lshButton;
    }

    public KeypadButton getRshButton() {
        return rshButton;
    }
}
