package ru.ldwx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public void print(String message) {
        System.out.println(message);
    }

    public String getExpression() {
        print("Введите пример:");
        String expression = "";
        try {
            expression = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression;
    }
}
