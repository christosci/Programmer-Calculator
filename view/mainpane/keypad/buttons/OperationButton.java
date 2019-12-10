public class OperationButton extends KeypadButton {
    private final Operation operation;

    public OperationButton(String s, Operation operation) {
        super(s);
        this.operation = operation;
        setId("operation-button");
    }

    public Operation getOperation() {
        return operation;
    }
}
