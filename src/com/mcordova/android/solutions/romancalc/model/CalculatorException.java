package com.mcordova.android.solutions.romancalc.model;

/**
 *
 * @author Mark Philip Cordova
 */
public class CalculatorException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// TODO: refactor to convert these to enum instead. will produce cleaner code.
	public static String NUMBER_FORMAT_ERROR = "NUMBER_FORMAT_ERROR";
    public static String INPUT_NOT_RECOGNIZED = "INPUT_NOT_RECOGNIZED";
    public static String ERROR_EVALUATING_POSTFIX = "ERROR_EVALUATING_POSTFIX";
    public static String OPERATOR_INVALID_PARAMETERS = "OPERATOR_INVALID_PARAMETERS";
    public static String UNMATCHED_PARENTHESIS = "UNMATCHED_PARENTHESIS";
    public static String BLANK_INPUT = "BLANK_INPUT";
    
    public CalculatorException(String EXCEPTION_TYPE, String exceptionDetails) {
        this.mExceptionType = EXCEPTION_TYPE;
        this.mExceptionDetails = exceptionDetails;
    }

    public String getExceptionType() {
        return this.mExceptionType;
    }

    public String getExceptionDetails() {
        return this.mExceptionDetails;
    }

    private String mExceptionType;
    private String mExceptionDetails;
}

