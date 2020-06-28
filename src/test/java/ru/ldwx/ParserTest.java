package ru.ldwx;


import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void simpleExpression() throws ExpressionException {
        String expression = "8+5";
        Queue<String> parsedExpression = new LinkedList<>();
        parsedExpression.offer("8");
        parsedExpression.offer("5");
        parsedExpression.offer("+");
        assertEquals(parsedExpression, new Parser().parseExpression(expression));
    }

    @Test
    public void complexExpression() throws ExpressionException {
        String expression = "(8+2*5)/(1+3*2-4)";
        Queue<String> parsedExpression = new LinkedList<>();
        parsedExpression.offer("8");
        parsedExpression.offer("2");
        parsedExpression.offer("5");
        parsedExpression.offer("*");
        parsedExpression.offer("+");
        parsedExpression.offer("1");
        parsedExpression.offer("3");
        parsedExpression.offer("2");
        parsedExpression.offer("*");
        parsedExpression.offer("+");
        parsedExpression.offer("4");
        parsedExpression.offer("-");
        parsedExpression.offer("/");
        assertEquals(parsedExpression, new Parser().parseExpression(expression));
    }

    @Test
    public void expressionWithDouble() throws ExpressionException {
        String expression = "8.45+5.27";
        Queue<String> parsedExpression = new LinkedList<>();
        parsedExpression.offer("8.45");
        parsedExpression.offer("5.27");
        parsedExpression.offer("+");
        assertEquals(parsedExpression, new Parser().parseExpression(expression));
    }

    @Test(expected = ExpressionException.class)
    public void parserBracketException() throws ExpressionException {
        new Parser().parseExpression("2+8)");
    }

    @Test(expected = ExpressionException.class)
    public void parserSizeException() throws ExpressionException {
        new Parser().parseExpression("");
    }

    @Test(expected = ExpressionException.class)
    public void parserNullExpressionException() throws ExpressionException {
        new Parser().parseExpression(null);
    }

    @Test(expected = ExpressionException.class)
    public void parserOperationException() throws ExpressionException {
        new Parser().parseExpression("2/+8)");
    }
}