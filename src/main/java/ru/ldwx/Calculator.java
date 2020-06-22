package ru.ldwx;

import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Calculator {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private final Parser parser = new Parser();

    public Double calculate(String input) {
        Deque<String> operators = parser.parseExpression(input);
        Deque<Double> calculationResults = new LinkedList<>();
        while (!operators.isEmpty()) {
            String currentOperator = operators.pollFirst();
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

    private Double calc(Double firstNumber, Double secondNumber, String currentOperator) {
        switch (currentOperator) {
            case "+":
                return secondNumber + firstNumber;
            case "-":
                return secondNumber - firstNumber;
            case "*":
                return secondNumber * firstNumber;
            case "/":
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
