package com.mcordova.android.solutions.romancalc.controller;

import com.mcordova.android.solutions.romancalc.RomanCalculator;

public interface ICalculatorController {
	public void initialize(RomanCalculator romanCalculator);
	public void activateController();
	public void deactivateController();	
}
