package com.zvil.shefing;

import java.util.HashMap;

/**
 * A class with a static 'process' method that actually does the calculation (and handles exceptions)
 * @author Zvi Lifshitz
 */
public class Calculate {
    // Initiaize a static calculator mapping
    private static final HashMap<String, Calculator> calcMap = new HashMap<>() {
        {
            put("plus", new PlusCalculator());
            put("minus", new MinusCalculator());
            put("multiply", new MultiplyCalculator());
            put("divide", new DivideCalculator());
        }
    };
    
    private static final String NO_OPERATOR = "Operator is missing";
    private static final String UNRECOGNIZED = "Unrecognized operator '%s'";
    private static final String MISSING_OPERAND = "The %s operand is missing";
    private static final String PARSE_ERROR = "%s is not an integer number";
    private static final String INT_RESULT = "%1$d%2$s%3$d=%4$d";
    private static final String REAL_RESULT = "%1$d%2$s%3$d=%4$s";
    
    /**
     * The method that does the work
     * @param operator  can be "plus", "minus", "multiply", "divide"
     * @param left      left operand (as a string)
     * @param right     right operand (as a string)
     * @return the result string
     */
    public static String process(String left, String operator, String right) {
        // Get the appropriate calculator. If not found return an error string
        if (operator == null)
            return NO_OPERATOR;
        Calculator calculator = calcMap.get(operator.toLowerCase());
        if (calculator == null)
            return String.format(UNRECOGNIZED, operator);
        
        // Calculate and format the result, or return an error string in case of an error.
        // Format the result nicely (division can yield a non-integer value).
        try {
            int nLeft = parseOperand(left, "left");
            int nRight = parseOperand(right, "right");
            double dResult =  calculator.calculate(nLeft, nRight);
            String op = calculator.getOperator();
            return dResult == (long)dResult ?
                String.format(INT_RESULT, nLeft, op, nRight, (long)dResult) :
                String.format(REAL_RESULT, nLeft, op, nRight, dResult);
        } catch(IllegalArgumentException | ArithmeticException ex) {
            return ex.getMessage();
        }
    }
    
    /**
     * Parse a string into an integer.
     * @param operand   the operand to parse
     * @param side      'left' or 'right' (used if the operand is null)
     * @return the value of the operand as an integer number
     * @throws IllegalArgumentException     the the operand is null or causes a parsing error
     */
    private static int parseOperand(String operand, String side) throws IllegalArgumentException {
        if (operand == null)
            throw new IllegalArgumentException(String.format(MISSING_OPERAND, side));
        try {
            return Integer.parseInt(operand);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format(PARSE_ERROR, operand));
        }
    }
}
