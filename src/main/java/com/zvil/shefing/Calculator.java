package com.zvil.shefing;

/**
 * An interface for specific arithmetic calculators
 * @author Zvi Lifshitz
 */
public interface Calculator {

    /**
     * Implement this method to provide calculators for the four arithmetic operations
     * @param left      the left operand
     * @param right     the right operand
     * @return returns the result of the operation
     * @throws ArithmeticException if the implementation encounters an arithmetic exception, such as divide by zero
     */
    public double calculate(int left, int right) throws ArithmeticException;

    /**
     * Implement this method to return the operator for this calculator
     * @return '+', '-', '*' or '/'
     */
    public String getOperator();
}
