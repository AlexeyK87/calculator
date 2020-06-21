package ru.ldwx;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private final String operations = "+-*/()";

    public Queue<String> parseExpression(String expression) {
        Deque<String> parsedExpression = new LinkedList<>();
        Deque<String> operatorsStack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            String currentOperator = String.valueOf(expression.charAt(i));
            if (operations.contains(currentOperator)) {
                if (operatorsStack.isEmpty()) {
                    if (currentOperator.equals(")")) {
                        throw new IllegalArgumentException();
                    }
                    operatorsStack.offerFirst(currentOperator);
                } else {
                    String firstOperatorInStack = operatorsStack.peekFirst();
                    switch (currentOperator) {
                        case "(":
                            operatorsStack.offerFirst(currentOperator);
                            break;
                        case ")":
                            if (firstOperatorInStack.equals("(")) {
                                operatorsStack.pollFirst();
                            } else {
                                while (!"(".equals(firstOperatorInStack)) {
                                    firstOperatorInStack = operatorsStack.pollFirst();
                                    if (firstOperatorInStack == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (!"(".equals(firstOperatorInStack)) {
                                        parsedExpression.offerLast(firstOperatorInStack);
                                    }
                                }
                            }
                            break;
                        case "+":
                        case "-":
                            if (firstOperatorInStack.equals("(")) {
                                operatorsStack.offerFirst(currentOperator);
                            } else {
                                while (!operatorsStack.isEmpty()) {
                                    firstOperatorInStack = operatorsStack.pollFirst();

                                    if (operatorsStack.isEmpty()) {
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    } else if ("(".equals(firstOperatorInStack)) {
                                        operatorsStack.offerFirst(firstOperatorInStack);
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    } else {
                                        parsedExpression.offerLast(firstOperatorInStack);
                                    }
                                }
                            }
                            break;
                        case "*":
                        case "/":
                            if (firstOperatorInStack.equals("+")
                                    || firstOperatorInStack.equals("-")
                                    || firstOperatorInStack.equals("(")) {
                                operatorsStack.offerFirst(currentOperator);
                            } else {
                                while (true) {
                                    firstOperatorInStack = operatorsStack.pollFirst();
                                    if (firstOperatorInStack == null) {
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    }
                                    if (firstOperatorInStack.equals("*") || firstOperatorInStack.equals("/")) {
                                        parsedExpression.offerLast(firstOperatorInStack);
                                    } else {
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    }
                                }
                            }
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                }
            } else {
                parsedExpression.offerLast(currentOperator);
            }
        }
        while (!operatorsStack.isEmpty()) {
            parsedExpression.offerLast(operatorsStack.pollFirst());
        }
        return parsedExpression;
    }
}
