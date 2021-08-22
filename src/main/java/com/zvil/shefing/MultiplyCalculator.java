package com.zvil.shefing;

/**
 * Calculator implementation of multiplication
 * @author Zvi Lifshitz
 */
public class MultiplyCalculator implements Calculator {

    @Override
    public double calculate(int left, int right) throws ArithmeticException {
        return left * right;
    }

    @Override
    public String getOperator() {
        return "*";
    }
}
