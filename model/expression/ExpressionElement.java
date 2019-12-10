public class ExpressionElement {
    private Number operand = null;
    private Operation operator = null;
    private Expression expression = null;
    private boolean parenthesisClosed = false;
    private boolean negated = false;

    public ExpressionElement(Number operand) {
        this.operand = operand;
    }

    public ExpressionElement(Operation operator) {
        this.operator = operator;
    }

    public ExpressionElement(Expression expression) {
        this.expression = expression;
    }

    public void setParenthesisClosed(boolean parenthesisClosed) {
        this.parenthesisClosed = parenthesisClosed;
    }

    public Number getOperand() {
        return operand;
    }

    public Operation getOperator() {
        return operator;
    }

    public boolean isParenthesisClosed() {
        return parenthesisClosed;
    }

    public Expression getExpression() {
        return expression;
    }

    public void negate() {
        negated = !negated;
    }

    public boolean isNegated() {
        return negated;
    }

    public String toString(NumberSystem numberSystem, Word word) {
        return (negated ? "Negate" : "") +
                (operand == null ? "" : operand.getNumber(numberSystem, word)) +
                (operator == null ? "" : " " + operator.getHumanReadableSymbol() + " ") +
                (expression == null ? "" : "(" + expression.toString(numberSystem, word)) +
                (parenthesisClosed ? ")" : "");
    }

}
