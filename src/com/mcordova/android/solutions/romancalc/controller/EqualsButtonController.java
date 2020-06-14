package com.mcordova.android.solutions.romancalc.controller;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.solutions.android.romancalc.R;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EqualsButtonController implements OnClickListener, ICalculatorController {
	private static EqualsButtonController INSTANCE = null;
	public static EqualsButtonController getInstance() {
		if(EqualsButtonController.INSTANCE == null) {
			EqualsButtonController.INSTANCE = new EqualsButtonController();
		}		
		return EqualsButtonController.INSTANCE;
	}
	
	private EqualsButtonController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
	}
	
	public void onClick(View view) {
		Log.v("onClick", "" + this.mBtnEquals.getText().toString());
		this.mCalculatorModel.evaluateMathExpression();
	}
	
	public void activateController() {
	}

	public void deactivateController() {
	}
	
	public void initialize(RomanCalculator romanCalculator) {
		this.mContext = romanCalculator;
		this.mBtnEquals = (Button) this.mContext.findViewById(R.id.btnEquals);
		this.mBtnEquals.setOnClickListener(this);
		this.mBtnEquals.setText("=");
		this.mBtnEquals.getBackground().setColorFilter(0xCC5DFC0A, PorterDuff.Mode.MULTIPLY);
	}	
	private RomanCalculator mContext = null;
	private Button mBtnEquals = null;	
	private CalculatorModel mCalculatorModel = null;
}
