package com.mcordova.android.solutions.romancalc.model.updates;

public class CalculatorError {
	public CalculatorError(String error) {
		this.mError = error;
	}
	
	public String getError() {
		return this.mError;
	}
	
	private String mError;
}
