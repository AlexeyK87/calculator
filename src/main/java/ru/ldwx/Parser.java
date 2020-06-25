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
        Deque<String> parsedExpression = new LinkedList<>();
        Deque<String> operatorsStack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            String firstOperatorInStack;
            switch (currentChar) {
                case '(':
                    operatorsStack.offerFirst("(");
                    break;
                case ')':
                    firstOperatorInStack = operatorsStack.poll();
                    if ("(".equals(firstOperatorInStack)) {
                        operatorsStack.poll();
                    } else {
                        do {
                            parsedExpression.offer(firstOperatorInStack);
                            firstOperatorInStack = operatorsStack.poll();
                        } while (!"(".equals(firstOperatorInStack));
                    }
                    break;
                case '+':
                case '-':
                    if (operatorsStack.isEmpty()) {
                        operatorsStack.offerFirst(String.valueOf(currentChar));
                        break;
                    }
                    firstOperatorInStack = operatorsStack.peek();
                    if ("(".equals(firstOperatorInStack)) {
                        operatorsStack.offerFirst(String.valueOf(currentChar));
                    } else {
                        while (!operatorsStack.isEmpty()) {
                            firstOperatorInStack = operatorsStack.poll();
                            if (operatorsStack.isEmpty()) {
                                operatorsStack.offerFirst(String.valueOf(currentChar));
                                break;
                            } else if ("(".equals(firstOperatorInStack)) {
                                operatorsStack.offerFirst(firstOperatorInStack);
                                operatorsStack.offerFirst(String.valueOf(currentChar));
                                break;
                            } else {
                                parsedExpression.offer(firstOperatorInStack);
                            }
                        }
                    }
                    break;
                case '*':
                case '/':
                    if (operatorsStack.isEmpty()) {
                        operatorsStack.offerFirst(String.valueOf(currentChar));
                        break;
                    }
                    firstOperatorInStack = operatorsStack.peekFirst();
                    if ("+".equals(firstOperatorInStack)
                            || "-".equals(firstOperatorInStack)
                            || "(".equals(firstOperatorInStack)) {
                        operatorsStack.offerFirst(String.valueOf(currentChar));
                    } else {
                        while (true) {
                            firstOperatorInStack = operatorsStack.poll();
                            if (firstOperatorInStack == null) {
                                operatorsStack.offerFirst(String.valueOf(currentChar));
                                break;
                            }
                            if ("*".equals(firstOperatorInStack) || "/".equals(firstOperatorInStack)) {
                                parsedExpression.offer(firstOperatorInStack);
                            } else {
                                operatorsStack.offerFirst(String.valueOf(currentChar));
                                break;
                            }
                        }
                    }
                    break;
                default:
                    StringBuilder builder = new StringBuilder();
                    String operations = "+-*/()";
                    String currentOperator = String.valueOf(currentChar);
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
