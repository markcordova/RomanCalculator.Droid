package com.mcordova.android.solutions.romancalc.model;

import java.util.Observable;
import java.util.Stack;
import java.util.Vector;

import android.util.Log;

import com.mcordova.android.solutions.romancalc.model.updates.CalculatorError;
import com.mcordova.android.solutions.romancalc.model.updates.CalculatorOutput;

public class CalculatorModel extends Observable {
	private static CalculatorModel INSTANCE = null;
	public static CalculatorModel getInstance() {
		if(CalculatorModel.INSTANCE == null) {
			CalculatorModel.INSTANCE = new CalculatorModel();
		}		
		return CalculatorModel.INSTANCE;
	}
	
	private CalculatorModel() {
		this.mCalculatorMode = CalculatorMode.ROMAN_MODE;
		this.mCalculatorCore = CalculatorCore.getInstance();
		this.mRomanNumeralsConverter = RomanNumeralsConverter.getInstance();
		
		this.mRomanNumeralBuffer = new StringBuffer();
		this.mMathExpressionBuffer = new StringBuffer();
	}
	
	public CalculatorMode getCalculatorMode() {
		return this.mCalculatorMode;
	}
	
	public void switchMode(CalculatorMode mode) {
		this.mCalculatorMode = mode;
	}
	
	public void appendOperator(String op) {
		if(op != null) {
			this.mMathExpressionBuffer.append(op.toCharArray());		
			this.updateOutput(this.mMathExpressionBuffer.toString());			
		}
	}

	public void appendArabicNumeral(String num) {
		if(num != null) {
			this.mMathExpressionBuffer.append(num.toCharArray());		
			this.updateOutput(this.mMathExpressionBuffer.toString());			
		}
	}	
	
	public Vector<RomanNumeralValues> appendRomanNumeral(String num) {
		Vector<RomanNumeralValues> retval = null;
		if(num != null) {			
			this.mRomanNumeralBuffer.append(num.toCharArray());
			this.mMathExpressionBuffer.append(num.toCharArray());			
			this.updateOutput(this.mMathExpressionBuffer.toString());
			
			retval = this.mRomanNumeralsConverter.getNextValidNumeral(this.mRomanNumeralBuffer.toString());
		}	
		return retval;
	}	 

	public void deleteLastCharacter() {
		if(this.mMathExpressionBuffer.length() > 0){
			this.mMathExpressionBuffer.deleteCharAt(this.mMathExpressionBuffer.length() - 1);
			this.updateOutput(this.mMathExpressionBuffer.toString());			
		}
	}
	
	public void clearMathExpression() {
		this.mMathExpressionBuffer.setLength(0);
		this.updateOutput(this.mMathExpressionBuffer.toString());
	}
	
	public void evaluateMathExpression() {
		try {
			if(this.mMathExpressionBuffer.length() == 0) {
				throw(new CalculatorException(CalculatorException.BLANK_INPUT, ""));
			}
			
			Log.v("CalculatorModel", "this.mMathExpressionBuffer = " + this.mMathExpressionBuffer.toString());
			
			// NOTE: tokenizeInput will not throw CalculatorException. these cases will be caught below.
			String[] tokens = this.mCalculatorCore.tokenizeInput(this.mMathExpressionBuffer.toString());
			Log.v("CalculatorModel", "string length = " + this.mMathExpressionBuffer.toString().length());
			Log.v("CalculatorModel", "num tokens = " + tokens.length);
			for(int i = 0; i < tokens.length; i++) {
				String token = tokens[i];
				if(!this.mCalculatorCore.isNumber(token) && !this.mCalculatorCore.isOperator(token) && !this.mCalculatorCore.isParenthesis(token)){
					// NOTE: leave invalid input as is. these will be caught by validateTokens.
					if(this.mRomanNumeralsConverter.validateRomanNumber(token) == true) {
						tokens[i] = "" + this.mRomanNumeralsConverter.convertToDouble(token);
					}
				}				
			}
			
			this.mCalculatorCore.validateTokens(tokens);
			
			// NOTE: now that we've converted (any) roman numerals, form string again and re-tokenize to catch special cases
			this.clearMathExpression();
			for(int i = 0; i < tokens.length; i++) {
				String token = tokens[i];
				this.mMathExpressionBuffer.append(token);
			}
			tokens = this.mCalculatorCore.tokenizeInput(this.mMathExpressionBuffer.toString());
			
			Stack<String> postfixQueue = this.mCalculatorCore.convertToPostfix(tokens);
			double answer = this.mCalculatorCore.evaluatePostfix(postfixQueue);			
			this.mMathExpressionBuffer.setLength(0);
			this.showAnswer(answer);
			
		} catch (CalculatorException e) {
			Log.v("CalculatorModel", "caught exception: " + e.getExceptionType());
			if(e.getExceptionType() == CalculatorException.ERROR_EVALUATING_POSTFIX) {
				this.showError("Error evaluating expression:");
			}
			else if(e.getExceptionType() == CalculatorException.NUMBER_FORMAT_ERROR) {
				this.showError("Error converting number: " + e.getExceptionDetails());		
			}
			else if(e.getExceptionType() == CalculatorException.INPUT_NOT_RECOGNIZED) {
				this.showError("Invalid input: " + e.getExceptionDetails());
			}
			else if(e.getExceptionType() == CalculatorException.OPERATOR_INVALID_PARAMETERS) {
				this.showError("Incorrect number of operands:");
			}
			else if(e.getExceptionType() == CalculatorException.UNMATCHED_PARENTHESIS) {
				this.showError("Unmatched parenthesis:");
			}						
			else if(e.getExceptionType() == CalculatorException.BLANK_INPUT) {
				// NOTE: do nothing
			}
			else {
				this.showError("Uncaught exception:");
			}
		}
	}
	
	private void showAnswer(double answer) {
		String answerString = "";
		if(this.mCalculatorMode == CalculatorMode.BASIC_MODE) {
			answerString += answer;			
			if(answerString.endsWith(".0")) {
				answerString = answerString.substring(0, answerString.length() - 2);
			}
			this.appendAnswer(answer);			
		}
		else if(this.mCalculatorMode == CalculatorMode.ROMAN_MODE) {
			String romanAnswer = this.mRomanNumeralsConverter.convertFromDouble(answer);
			if(this.mRomanNumeralsConverter.validateRomanNumber(romanAnswer) == true) {
				answerString += this.mRomanNumeralsConverter.convertFromDouble(answer);
				this.mMathExpressionBuffer.append(answerString);
			}
			else {
				// NOTE: if answer is "Infinity" or "Nan" which cannot be converted to roman numerals
				answerString += answer;
				this.appendAnswer(answer);
			}			
		}
		
		this.updateOutput(answerString);
	}
	
	private void appendAnswer(double answer) {
		String answerString = "";
		// NOTE: append answer as first entry in the next math expression, unless it is a NaN or Infinity
		if(! Double.isInfinite(answer) && !Double.isNaN(answer)) {
			answerString += answer;
			if(answerString.endsWith(".0")) {
				answerString = answerString.substring(0, answerString.length() - 2);
			}

			this.mMathExpressionBuffer.append(answerString);
		}						
		
	}
	
	private void updateOutput(String display) {
		this.setChanged();
		this.notifyObservers(new CalculatorOutput(display));		
	}
	
	private void showError(String error) {
		this.setChanged();
		this.notifyObservers(new CalculatorError(error + "\n" + this.mMathExpressionBuffer));
	}
		
	public enum CalculatorMode {
		BASIC_MODE,
		ROMAN_MODE;
	}
	
	private CalculatorCore mCalculatorCore = null;
	private RomanNumeralsConverter mRomanNumeralsConverter = null;
	
	private StringBuffer mMathExpressionBuffer = null;
	private StringBuffer mRomanNumeralBuffer = null;	
	private CalculatorMode mCalculatorMode = null;
	
}
