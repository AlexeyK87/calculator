package ru.ldwx;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTest {
    private final ExpressionValidator expressionValidator = new ExpressionValidator();

    @Test
    public void sizeValidationTest() {
        assertTrue(expressionValidator.validateSize("1+1"));
        assertTrue(expressionValidator.validateSize("4"));
        assertTrue(expressionValidator.validateSize("(4+8)*(2-6)/(4.42-6)+(12-1))"));

        assertFalse(expressionValidator.validateSize(null));
        assertFalse(expressionValidator.validateSize(""));
    }

    @Test
    public void bracketTest() {
        assertFalse(expressionValidator.validateBrackets("((("));
        assertFalse(expressionValidator.validateBrackets("("));
        assertFalse(expressionValidator.validateBrackets(")"));
        assertFalse(expressionValidator.validateBrackets("()()()()))"));
        assertFalse(expressionValidator.validateBrackets("(4+8)*(2-6)/(4.42-6)+(12-1))"));

        assertTrue(expressionValidator.validateBrackets("()()()()"));
        assertTrue(expressionValidator.validateBrackets("((()))"));
        assertTrue(expressionValidator.validateBrackets("()"));
        assertTrue(expressionValidator.validateBrackets("(4+8)*(2-6)/(4.42-6)+(12-1)"));
    }

    @Test
    public void numberValidationTest() {
        assertTrue(expressionValidator.validateNumber("3.75"));
        assertTrue(expressionValidator.validateNumber("3"));
        assertTrue(expressionValidator.validateNumber("-2"));

        assertFalse(expressionValidator.validateNumber("."));
        assertFalse(expressionValidator.validateNumber(".75"));
        assertFalse(expressionValidator.validateNumber("number"));
        assertFalse(expressionValidator.validateNumber("4..1"));
        assertFalse(expressionValidator.validateNumber("4."));
    }

    @Test
    public void operationValidationTest() {
        assertFalse(expressionValidator.validateOperations("1+1+"));
        assertFalse(expressionValidator.validateOperations("+1+1"));
        assertFalse(expressionValidator.validateOperations("4++4"));
        assertFalse(expressionValidator.validateOperations("2+(-1*3)"));
        assertFalse(expressionValidator.validateOperations("(4+8)*/(2-6)/(4.42-6)+(12-1)"));

        assertTrue(expressionValidator.validateOperations("1+1"));
        assertTrue(expressionValidator.validateOperations("4+5/7"));
        assertTrue(expressionValidator.validateOperations("(4+8)*(2-6)/(4.42-6)+(12-1)"));
    }
}