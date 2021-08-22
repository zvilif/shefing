package com.zvil.shefing;

import java.util.Objects;

/**
 * The calculation model object, converted from the posted JSON value.
 * We make it Comparable in order to use it as a key for the cash map
 * @author Zvi Lifshitz
 */
public class CalcModel implements Comparable<CalcModel>{
    private String operator;
    private String left, right;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public int compareTo(CalcModel o) {
        return this.equals(o) ? 0 : 1;
    }
    
    /**
     * Calculate a hash code for a field. If it is null return zero.
     * @param val the field
     * @return the value of the object's hash code or zero if the object is null
     */
    private int hash(String val) {
        return val == null ? 0 : val.hashCode();
    }

    /**
     * We generate the object hash code by xoring all fields' hash codes
     * @return 
     */
    @Override
    public int hashCode() {
        return hash(left) ^ hash(operator) ^ hash(right);
    }

    /**
     * Objects are equal if the 3 fields are equal
     * @param obj   object to compare to
     * @return      true if and only if all fields are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CalcModel))
            return false;
        CalcModel o = (CalcModel)obj;
        return
            Objects.equals(left, o.left) &&
            Objects.equals(operator, o.operator)&&
            Objects.equals(right, o.right);
    }
}
