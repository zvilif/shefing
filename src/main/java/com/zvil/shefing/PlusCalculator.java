package com.zvil.shefing;

/**
 * Calculator implementation of addition
 * @author Zvi Lifshitz
 */
public class PlusCalculator implements Calculator {

    @Override
    public double calculate(int left, int right) throws ArithmeticException {
        return left + right;
    }

    @Override
    public String getOperator() {
        return "+";
    }
}
