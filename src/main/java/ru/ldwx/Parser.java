package ru.ldwx;

import java.util.Deque;
import java.util.LinkedList;

public class Parser {
    private final ExpressionValidator validator = new ExpressionValidator();

    public Deque<String> parseExpression(String expression) throws ExpressionException {
        if (!validator.validateSize(expression)) {
            throw new ExpressionException("Выражение не должно быть пустым");
        }
        if (!validator.validateBrackets(expression)) {
            throw new ExpressionException("Скобки в выражении расставлены не корректно");
        }
        if (!validator.validateOperations(expression)) {
            throw new ExpressionException("В выражении не корректно проставлены операции");
        }
        String operations = "+-*/()";
        Deque<String> parsedExpression = new LinkedList<>();
        Deque<String> operatorsStack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            String currentOperator = String.valueOf(expression.charAt(i));
            if (operations.contains(currentOperator)) {
                if (operatorsStack.isEmpty()) {
                    operatorsStack.offer(currentOperator);
                } else {
                    String firstOperatorInStack = operatorsStack.peek();
                    switch (currentOperator) {
                        case "(":
                            operatorsStack.offerFirst(currentOperator);
                            break;
                        case ")":
                            if ("(".equals(firstOperatorInStack)) {
                                operatorsStack.poll();
                            } else {
                                while (!"(".equals(firstOperatorInStack)) {
                                    firstOperatorInStack = operatorsStack.poll();
                                    if (firstOperatorInStack == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (!"(".equals(firstOperatorInStack)) {
                                        parsedExpression.offer(firstOperatorInStack);
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
                                    firstOperatorInStack = operatorsStack.poll();

                                    if (operatorsStack.isEmpty()) {
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    } else if ("(".equals(firstOperatorInStack)) {
                                        operatorsStack.offerFirst(firstOperatorInStack);
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    } else {
                                        parsedExpression.offer(firstOperatorInStack);
                                    }
                                }
                            }
                            break;
                        case "*":
                        case "/":
                            if ("+".equals(firstOperatorInStack)
                                    || "-".equals(firstOperatorInStack)
                                    || "(".equals(firstOperatorInStack)) {
                                operatorsStack.offerFirst(currentOperator);
                            } else {
                                while (true) {
                                    firstOperatorInStack = operatorsStack.poll();
                                    if (firstOperatorInStack == null) {
                                        operatorsStack.offerFirst(currentOperator);
                                        break;
                                    }
                                    if ("*".equals(firstOperatorInStack) || "/".equals(firstOperatorInStack)) {
                                        parsedExpression.offer(firstOperatorInStack);
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
                StringBuilder builder = new StringBuilder();
                while (!operations.contains(currentOperator)) {
                    builder.append(currentOperator);
                    if (i < expression.length() - 1) {
                        String tmpOperator = String.valueOf(expression.charAt(i + 1));
                        if (operations.contains(tmpOperator)) {
                            break;
                        }
                        i++;
                        currentOperator = String.valueOf(expression.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!validator.validateNumber(builder.toString())) {
                    throw new ExpressionException("Не верный формат числа");
                }
                parsedExpression.offerLast(builder.toString());
            }
        }
        while (!operatorsStack.isEmpty()) {
            parsedExpression.offerLast(operatorsStack.pollFirst());
        }
        return parsedExpression;
    }
}
