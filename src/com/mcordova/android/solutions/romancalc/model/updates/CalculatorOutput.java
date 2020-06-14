package com.mcordova.android.solutions.romancalc.model.updates;

public class CalculatorOutput {
	public CalculatorOutput(String output) {
		this.mOutput = output;
	}
	
	public String getData() {
		return this.mOutput;
	}
	
	private String mOutput;
}
