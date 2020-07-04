package ru.ldwx;

public class Application {

    public static void main(String[] args) throws ExpressionException {
        ConsoleHelper consoleHelper = new ConsoleHelper();
        Double result = new Calculator().calculate(consoleHelper.getExpression());
        consoleHelper.print(result.toString());
    }
}
