package ru.ldwx;


import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void simpleExpressionTest() {
        String expression = "8+5";
        Queue<String> parsedExpression = new LinkedList<>();
        parsedExpression.offer("8");
        parsedExpression.offer("5");
        parsedExpression.offer("+");
        assertEquals(parsedExpression, new Parser().parseExpression(expression));
    }

    @Test
    public void hardExpression() {
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
}