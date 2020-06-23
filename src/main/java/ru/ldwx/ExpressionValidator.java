package ru.ldwx;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

public class ExpressionValidator {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean validateSize(String expression) {
        return expression != null && expression.length() != 0;
    }

    public boolean validateBrackets(String expression) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (currentChar == '(') {
                stack.push(currentChar);
            } else if (currentChar == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public boolean validateOperations(String expression) {
        String operations = "+-*/";
        if (operations.contains(expression.substring(0, 1))) {
            return false;
        }
        if (operations.contains(expression.substring(expression.length() - 1))) {
            return false;
        }
        expression = expression.replaceAll("\\(", "").replaceAll("\\)", "");
        for (int i = 1; i < expression.length() - 1; i++) {
            if (operations.indexOf(expression.charAt(i)) >= 0
                    && operations.indexOf(expression.charAt(i + 1)) >= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean validateNumber(String number) {
        if (number == null) {
            return false;
        }
        return pattern.matcher(number).matches();
    }
}
