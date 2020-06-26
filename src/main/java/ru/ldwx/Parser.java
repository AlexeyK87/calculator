package ru.ldwx;

import java.util.Deque;
import java.util.LinkedList;

public class Parser {
    private final ExpressionValidator validator = new ExpressionValidator();
    private final Deque<String> parsedExpression = new LinkedList<>();
    private final Deque<String> operatorsStack = new LinkedList<>();

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

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            switch (currentChar) {
                case '(':
                    operatorsStack.offerFirst("(");
                    break;
                case ')':
                    processRightBracket();
                    break;
                case '+':
                case '-':
                    processAdditionSubtraction(currentChar);
                    break;
                case '*':
                case '/':
                    processDivisionMultiplication(currentChar);
                    break;
                default:
                    StringBuilder builder = new StringBuilder();
                    builder.append(currentChar);
                    for (; i < expression.length() - 1; i++) {
                        char nextChar = expression.charAt(i + 1);
                        if ("+-*/()".indexOf(nextChar) >= 0) {
                            break;
                        }
                        builder.append(nextChar);
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

    private void processDivisionMultiplication(char currentChar) {
        if (operatorsStack.isEmpty()) {
            operatorsStack.offerFirst(String.valueOf(currentChar));
            return;
        }
        String firstOperatorInStack = operatorsStack.peekFirst();
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
    }

    private void processAdditionSubtraction(char currentChar) {
        if (operatorsStack.isEmpty()) {
            operatorsStack.offerFirst(String.valueOf(currentChar));
            return;
        }
        String firstOperatorInStack = operatorsStack.peek();
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
    }

    private void processRightBracket() {
        String firstOperatorInStack;
        firstOperatorInStack = operatorsStack.poll();
        if (!"(".equals(firstOperatorInStack)) {
            do {
                parsedExpression.offer(firstOperatorInStack);
                firstOperatorInStack = operatorsStack.poll();
            } while (!"(".equals(firstOperatorInStack));
        }
    }
}
