package com.mcordova.android.solutions.romancalc.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.android.solutions.romancalc.model.RomanNumeralValues;
import com.mcordova.solutions.android.romancalc.R;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RomanButtonsController implements OnClickListener, ICalculatorController {
	private static RomanButtonsController INSTANCE = null;
	public static RomanButtonsController getInstance() {
		if(RomanButtonsController.INSTANCE == null) {
			RomanButtonsController.INSTANCE = new RomanButtonsController();
		}		
		return RomanButtonsController.INSTANCE;
	}
	
	private RomanButtonsController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
	}
	
	public void onClick(View view) {
		Log.v("onClick", "" + this.buttons.get((Button) view).toString());
		Button btn = (Button) view;
		RomanNumeralValues num = this.buttons.get((Button) btn);
		if(num != null) {
			// NOTE: disabling dynamic deactivation of invalid buttons. invalid roman numerals will instead
			//	be caught during math expression evaluation for consistency of behaviour.			
			//Vector<RomanNumeralValues> validValues = this.mCalculatorModel.appendRomanNumeral(num.toString());
			//this.enableButtons(validValues);
			
			this.mCalculatorModel.appendRomanNumeral(num.toString());
		}		
	}
	
	/*
	private void enableButtons(Vector<RomanNumeralValues> validValues) {
		if(validValues != null) {
	    	for(Entry<Button, RomanNumeralValues> entry : this.buttons.entrySet()) {
	    		entry.getKey().setEnabled(false);
	    	}
	    	for(Entry<Button, RomanNumeralValues> entry : this.buttons.entrySet()) {
	    		if(validValues.contains(entry.getValue())) {
	    			entry.getKey().setEnabled(true);
	    		}
	    	}
		}
	}
	*/
	public void activateController() {
    	for(Entry<Button, RomanNumeralValues> entry : this.buttons.entrySet()) {
    		entry.getKey().setOnClickListener(this);
    		entry.getKey().setText(entry.getValue().toString());
    		entry.getKey().setEnabled(true);    		
    		
    		if(entry.getValue().equals(RomanNumeralValues.M)) {
    			entry.getKey().setText("i/M");
    		}
    		
    		if(entry.getValue().equals(RomanNumeralValues.S)) {
    			//entry.getKey().setEnabled(false);    			
    		}
    		
    		if(entry.getValue().equals(RomanNumeralValues.u)) {
    			entry.getKey().setText("•");
    		}    		
    	}        
	}

	public void deactivateController() {
	}	
	
	public void initialize(RomanCalculator romanCalculator) {
		this.mContext = romanCalculator;
		this.mapButtons();
	}
	
	private void mapButtons() {		
		this.buttons = new HashMap<Button, RomanNumeralValues>();
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey01), RomanNumeralValues.I);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey02), RomanNumeralValues.S);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey03), RomanNumeralValues.u);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey04), RomanNumeralValues.V);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey05), RomanNumeralValues.X);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey06), RomanNumeralValues.L);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey07), RomanNumeralValues.C);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey08), RomanNumeralValues.D);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey09), RomanNumeralValues.M);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey10), RomanNumeralValues.v);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey11), RomanNumeralValues.x);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey12), RomanNumeralValues.l);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey13), RomanNumeralValues.c);
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey14), RomanNumeralValues.d);		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey15), RomanNumeralValues.m);		
	}

	private RomanCalculator mContext = null;
	private HashMap<Button, RomanNumeralValues> buttons = null;	
	private CalculatorModel mCalculatorModel = null;
}
