package com.mcordova.android.solutions.romancalc.model;

import java.util.HashMap;

/**
 *
 * @author Mark Philip Cordova
 */
public enum CalculatorOperations {
    ADD(1, "+", true, true, true){ 
        double evaluate(double x, double y) {
            return x + y;
        }
    },
    SUBTRACT(1, "-", true, true, true) {
        double evaluate(double x, double y) {
            return x - y;
        }
    },
    MULTIPLY(2, "*", true, true, true) {
        double evaluate(double x, double y) {
            return x * y;
        }
    },
    DIVIDE(2, "/", true, true, true) {
        double evaluate(double x, double y) {
            return x / y;
        }
    },
    NEGATE(5, "_", false, true, true) {
        double evaluate(double x, double y) {
            return -x;
        }
    },
    SIN(3, "sin", false, true, true) {
        double evaluate(double x, double y) {
            return Math.sin(Math.toRadians(x));
        }
    },
    COS(3, "cos", false, true, true) {
        double evaluate(double x, double y) {
        	return Math.cos(Math.toRadians(x));
        }
    };

    CalculatorOperations(int precedence, String operation, boolean binaryOperator, boolean leftAssociative, boolean unaryPrefix) {
        this.mPrecedence = precedence;
        this.mOperation = operation;
        this.mIsBinaryOperator = binaryOperator;
        this.mIsLeftAssociative = leftAssociative;
        this.mIsUnaryPrefix = unaryPrefix;
    }

    int getPrecedence() {
        return this.mPrecedence;
    }

    String getOperation() {
        return this.mOperation;
    }

    boolean isBinaryOperator() {
        return this.mIsBinaryOperator;
    }

    boolean isLeftAssociative() {
        return this.mIsLeftAssociative;
    }

    boolean isUnaryPrefix() {
        return this.mIsUnaryPrefix;
    }
    
    abstract double evaluate(double x, double y);

    int mPrecedence;
    String mOperation;
    boolean mIsBinaryOperator;
    boolean mIsLeftAssociative;
    boolean mIsUnaryPrefix;

    static final HashMap<String, CalculatorOperations> OPERATOR_MAP = new HashMap<String, CalculatorOperations>();
    static {
        for(CalculatorOperations operation : CalculatorOperations.values()) {
            CalculatorOperations.OPERATOR_MAP.put(operation.getOperation(), operation);
        }
    }
}
