import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * The Expression class is an ArrayList containing ExpressionElements.
 * ExpressionElement objects can contain operands, operators, and Expression objects (for expressions within parentheses).
 */
public class Expression extends ArrayList<ExpressionElement> {
    private final Stack<ExpressionElement> unclosedParenthesisSubElements = new Stack<>();
    private boolean operatorRequired = false;
    private boolean operandRequired  = false;

    private Number lastParenthesisResult = null;

    public Expression() {
        super();
    }

    /**
     * Adds a new operand to the expression.
     * @param operand New operand.
     */
    public void addOperand(Number operand) {
        if (!operatorRequired) {
            if (unclosedParenthesisSubElements.empty() || unclosedParenthesisSubElements.peek().isParenthesisClosed())
                add(new ExpressionElement(operand));
            else
                unclosedParenthesisSubElements.peek().getExpression().addOperand(operand);
            operandRequired  = false;
            operatorRequired = true;
        }
    }

    /**
     * Adds a new operator to the expression.
     * @param operator New operator.
     */
    public void addOperator(Operation operator) {
        if (!operandRequired) {
            if (unclosedParenthesisSubElements.empty() || unclosedParenthesisSubElements.peek().isParenthesisClosed())
                add(new ExpressionElement(operator));
            else
                unclosedParenthesisSubElements.peek().getExpression().addOperator(operator);
            operandRequired  = true;
            operatorRequired = false;
        }
    }

    /**
     * Creates a new ExpressionElement containing an Expression object.
     * @return The new Expression element.
     */
    public ExpressionElement openParenthesis() {
        if (!isEmpty()) {
            ExpressionElement last = get(size() - 1);
            if (last.getExpression() != null)
                return unclosedParenthesisSubElements.push(last.getExpression().openParenthesis());
        }
        Expression sublist = new Expression();
        ExpressionElement ep = new ExpressionElement(sublist);
        add(ep);
        return unclosedParenthesisSubElements.push(ep);
    }

    /**
     * Closes the last parenthesis
     */
    public void closeParenthesis() {
        if (!unclosedParenthesisSubElements.isEmpty()) {
            unclosedParenthesisSubElements.peek().setParenthesisClosed(true);
            lastParenthesisResult = EvaluateExpression.evaluate(unclosedParenthesisSubElements.pop().getExpression());
            operatorRequired = true;
        }
    }

    /**
     * Negates the last element.
     */
    public void negateLast() {
        ExpressionElement last = get(size() - 1);
        if (last.getExpression() != null) {
            if (last.isParenthesisClosed() && !last.isNegated()) {
                last.negate();
            }
            else if (last.isParenthesisClosed() && last.isNegated()) {
                Expression inner = new Expression();
                inner.add(last);
                ExpressionElement outer = new ExpressionElement(inner);
                outer.setParenthesisClosed(true);
                outer.negate();
                remove(size()-1);
                add(outer);
            }
            else
                last.getExpression().negateLast();
        }
        else {
            Expression negatedOperand = new Expression();
            negatedOperand.addOperand(last.getOperand());
            ExpressionElement newElement = new ExpressionElement(negatedOperand);
            newElement.setParenthesisClosed(true);
            newElement.negate();
            remove(size()-1);
            add(newElement);
        }
        operatorRequired = true;
    }

    public Number getLastParenthesisResult() {
        return lastParenthesisResult;
    }

    /**
     * Evaluates the expression.
     * @return The result of the expression.
     */
    public Number getResult() {
        try {
            return EvaluateExpression.evaluate(this);
        }
        catch (ArithmeticException ae) {
            operandRequired = true;
            clear();
            throw ae;
        }
    }

    /**
     * Resets the expression.
     */
    @Override
    public void clear() {
        super.clear();
        operatorRequired = operandRequired = false;
        unclosedParenthesisSubElements.clear();
    }

    @Override
    public String toString() {
        Iterator<ExpressionElement> i = iterator();
        StringBuilder sb = new StringBuilder();

        while (i.hasNext()) {
            ExpressionElement ep = i.next();
            sb.append(ep);
        }

        return sb.toString();
    }

    public String toString(NumberSystem numberSystem, Word word) {
        Iterator<ExpressionElement> i = iterator();
        StringBuilder sb = new StringBuilder();

        while (i.hasNext()) {
            ExpressionElement ep = i.next();
            sb.append(ep.toString(numberSystem, word));
        }

        return sb.toString();
    }
}
