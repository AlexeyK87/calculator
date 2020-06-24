package ru.ldwx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void simpleTest() throws ExpressionException {
        assertEquals(Double.valueOf(6), new Calculator().calculate("(8+2*5)/(1+3*2-4)"));
        assertEquals(Double.valueOf(2), new Calculator().calculate("1+1"));
        assertEquals(Double.valueOf(1), new Calculator().calculate("1/1"));
        assertEquals(Double.valueOf(10), new Calculator().calculate("8.25+1.75"));
    }

    @Test(expected = ExpressionException.class)
    public void divisionByZeroTest() throws ExpressionException {
        new Calculator().calculate("1/0");
    }

    @Test(expected = ExpressionException.class)
    public void bracketExceptionTest() throws ExpressionException {
        new Calculator().calculate("(1/2)*7)");
    }

    @Test(expected = ExpressionException.class)
    public void expressionSizeTest() throws ExpressionException {
        new Calculator().calculate("");
    }

    @Test(expected = ExpressionException.class)
    public void incorrectOperatorsTest() throws ExpressionException {
        new Calculator().calculate("4++5");
    }

    @Test(expected = ExpressionException.class)
    public void incorrectNumberTest() throws ExpressionException {
        new Calculator().calculate("4.5+7.25+4..1");
    }
}