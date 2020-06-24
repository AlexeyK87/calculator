package ru.ldwx;

import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Calculator {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private final Parser parser = new Parser();

    public Double calculate(String input) throws ExpressionException {
        Deque<String> operators = parser.parseExpression(input);
        Deque<Double> calculationResults = new LinkedList<>();
        while (!operators.isEmpty()) {
            String currentOperator = operators.poll();
            if (isNumeric(currentOperator)) {
                calculationResults.offer(Double.parseDouble(currentOperator));
            } else {
                Double firstNumber = calculationResults.pollLast();
                Double secondNumber = calculationResults.pollLast();
                Double result = calc(firstNumber, secondNumber, currentOperator);
                calculationResults.offer(result);
            }
        }
        return calculationResults.poll();
    }

    private Double calc(Double firstNumber, Double secondNumber, String currentOperator) throws ExpressionException {
        switch (currentOperator) {
            case "+":
                return secondNumber + firstNumber;
            case "-":
                return secondNumber - firstNumber;
            case "*":
                return secondNumber * firstNumber;
            case "/":
                if (firstNumber.equals((double) 0)) {
                    throw new ExpressionException("Деление на ноль");
                }
                return secondNumber / firstNumber;
        }
        return 0d;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
