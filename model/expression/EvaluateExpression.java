import java.util.ArrayList;
import java.util.Stack;

public class EvaluateExpression {
    /**
     * Evaluates a given Expression object.
     * @param ex The Expression object to evaluate.
     * @return Number object of the result.
     */
    public static Number evaluate(Expression ex) {
        ArrayList<ExpressionElement> expression = new ArrayList<>(ex); // create a copy
        Stack<Number>    operands  = new Stack<>();
        Stack<Operation> operators = new Stack<>();

        for (int i=0; i<expression.size(); i++) {
            ExpressionElement ee = expression.get(i);
            if (ee.getOperand() != null) {
                if (expression.size() == 1) {
                    return ee.getOperand();
                }
                operands.push(ee.getOperand());
            }
            else if (ee.getOperator() != null) {
                while (!operators.empty() && hasPrecedence(ee.getOperator(), operators.peek())) {
                    long result = applyOperation(operators.pop(), operands.pop().getQWORD(), operands.pop().getQWORD());
                    Number value = new Number(String.valueOf(result), NumberSystem.DEC);
                    operands.push(value);
                }
                operators.push(ee.getOperator());
            }
            else if (ee.getExpression() != null) {
                if (ee.isParenthesisClosed()) {
                    Number parenthesisResult = evaluate(ee.getExpression());
                    if (ee.isNegated()) {
                        parenthesisResult.negate();
                    }
                    operands.push(parenthesisResult);
                }
                else {
                    expression.remove(i);
                    expression.addAll(ee.getExpression());
                    i-=1;
                }
            }
        }

        while (!operators.empty()) {
            long result = applyOperation(operators.pop(), operands.pop().getQWORD(), operands.pop().getQWORD());
            Number value = new Number(String.valueOf(result), NumberSystem.DEC);
            operands.push(value);
        }
        return operands.pop();
    }

    private static long applyOperation(Operation op, long b, long a) {
        switch (op) {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                if (a == 0 && b == 0)
                    throw new ArithmeticException("Result is undefined");
                else if (b == 0)
                    throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            case MODULO:
                if (b == 0)
                    throw new ArithmeticException("Result is undefined");
                return a % b;
        }
        return 0;
    }

    private static boolean hasPrecedence(Operation op1, Operation op2) {
        return (op1 != Operation.MULTIPLY && op1 != Operation.DIVIDE && op1 != Operation.MODULO) ||
                (op2 != Operation.ADD && op2 != Operation.SUBTRACT);
    }
}
