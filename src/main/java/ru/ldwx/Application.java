package ru.ldwx;

public class Application {

    public static void main(String[] args) throws ExpressionException {
        ConsoleHelper consoleHelper = new ConsoleHelper();
        String expression = consoleHelper.getExpression();
        Double result = new Calculator().calculate(expression);
        consoleHelper.print(result.toString());
    }
}
