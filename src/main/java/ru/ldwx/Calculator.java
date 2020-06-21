package ru.ldwx;

import java.util.Queue;

public class Calculator {
    private final Parser parser = new Parser();

    public double calculate(String input) {
        Queue<String> operators = parser.parseExpression(input);

        return 0;
    }
}
