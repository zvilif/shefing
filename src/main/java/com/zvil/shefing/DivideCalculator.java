package com.zvil.shefing;

/**
 * Calculator implementation of multiplication
 * @author Zvi Lifshitz
 */
public class DivideCalculator implements Calculator {
    private static final String DIVIDE_BY_ZERO = "Divide by zero";

    @Override
    public double calculate(int left, int right) throws ArithmeticException {
        if (right == 0)
            throw new ArithmeticException(DIVIDE_BY_ZERO);
        return left / (double)right;
    }

    @Override
    public String getOperator() {
        return "/";
    }
}
