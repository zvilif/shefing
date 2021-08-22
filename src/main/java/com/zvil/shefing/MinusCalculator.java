package com.zvil.shefing;

/**
 * Calculator implementation of subtraction
 * @author Zvi Lifshitz
 */
public class MinusCalculator implements Calculator {

    @Override
    public double calculate(int left, int right) throws ArithmeticException {
        return left - right;
    }

    @Override
    public String getOperator() {
        return "-";
    }
}
