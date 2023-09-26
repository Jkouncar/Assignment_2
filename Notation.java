import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Notation {
    // Define the precedence of operators
    private static final Map<Character, Integer> operatorPrecedence = new HashMap<>();

    static {
        operatorPrecedence.put('+', 1);
        operatorPrecedence.put('-', 1);
        operatorPrecedence.put('*', 2);
        operatorPrecedence.put('/', 2);
    }

    public static String convertInfixToPostfix(String infix) throws InvalidNotationFormatException {
        StringBuilder postfix = new StringBuilder();
        Deque<Character> operatorStack = new ArrayDeque<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    throw new InvalidNotationFormatException("Invalid infix expression");
                }
            } else if (operatorPrecedence.containsKey(c)) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                       operatorPrecedence.get(c) <= operatorPrecedence.get(operatorStack.peek())) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(c);
            } else {
                throw new InvalidNotationFormatException("Invalid character in infix expression");
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(') {
                throw new InvalidNotationFormatException("Mismatched parentheses in infix expression");
            }
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    public static String convertPostfixToInfix(String postfix) throws InvalidNotationFormatException {
        Deque<String> operandStack = new ArrayDeque<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                operandStack.push(String.valueOf(c));
            } else if (operatorPrecedence.containsKey(c)) {
                if (operandStack.size() < 2) {
                    throw new InvalidNotationFormatException("Invalid postfix expression");
                }
                String operand2 = operandStack.pop();
                String operand1 = operandStack.pop();
                String result = "(" + operand1 + c + operand2 + ")";
                operandStack.push(result);
            } else {
                throw new InvalidNotationFormatException("Invalid character in postfix expression");
            }
        }

        if (operandStack.size() != 1) {
            throw new InvalidNotationFormatException("Invalid postfix expression");
        }

        return operandStack.pop();
    }

    public static double evaluatePostfixExpression(String postfix) throws InvalidNotationFormatException {
        Deque<Double> operandStack = new ArrayDeque<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                operandStack.push(Double.parseDouble(String.valueOf(c)));
            } else if (operatorPrecedence.containsKey(c)) {
                if (operandStack.size() < 2) {
                    throw new InvalidNotationFormatException("Invalid postfix expression");
                }
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();
                double result;
                switch (c) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        result = operand1 / operand2;
                        break;
                    default:
                        throw new InvalidNotationFormatException("Invalid operator in postfix expression");
                }
                operandStack.push(result);
            } else {
                throw new InvalidNotationFormatException("Invalid character in postfix expression");
            }
        }

        if (operandStack.size() != 1) {
            throw new InvalidNotationFormatException("Invalid postfix expression");
        }

        return operandStack.pop();
    }
}
