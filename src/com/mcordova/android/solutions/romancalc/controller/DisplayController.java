package com.mcordova.android.solutions.romancalc.controller;

import java.util.Observable;
import java.util.Observer;

import android.widget.EditText;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.android.solutions.romancalc.model.updates.CalculatorError;
import com.mcordova.android.solutions.romancalc.model.updates.CalculatorOutput;
import com.mcordova.solutions.android.romancalc.R;

public class DisplayController implements Observer {
	private static DisplayController INSTANCE = null;
	public static DisplayController getInstance() {
		if(DisplayController.INSTANCE == null) {
			DisplayController.INSTANCE = new DisplayController();
		}		
		return DisplayController.INSTANCE;
	}
	
	private DisplayController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
		this.mCalculatorModel.addObserver(this);
	}

	public void initialize(RomanCalculator romanCalculator) {
		this.mTxtOutput = (EditText) romanCalculator.findViewById(R.id.txtOutput);
	}
	
	public void update(Observable observable, Object update) {
		if(update != null){
			if(update.getClass().equals(CalculatorOutput.class)) {
				CalculatorOutput data = (CalculatorOutput) update;
				this.mTxtOutput.setText(data.getData().toString());
			}
			else if(update.getClass().equals(CalculatorError.class)) {
				CalculatorError data = (CalculatorError) update;				
				this.mTxtOutput.setText(data.getError().toString());
			}
		}
	}

	private CalculatorModel mCalculatorModel = null;
	private EditText mTxtOutput = null;
}
