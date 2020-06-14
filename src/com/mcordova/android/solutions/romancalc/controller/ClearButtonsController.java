package com.mcordova.android.solutions.romancalc.controller;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.solutions.android.romancalc.R;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClearButtonsController implements OnClickListener, ICalculatorController {
	private static ClearButtonsController INSTANCE = null;
	public static ClearButtonsController getInstance() {
		if(ClearButtonsController.INSTANCE == null) {
			ClearButtonsController.INSTANCE = new ClearButtonsController();
		}		
		return ClearButtonsController.INSTANCE;
	}
	
	private ClearButtonsController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
	}
	
	public void onClick(View view) {
		Button btn = (Button) view;
		if(btn.equals(this.mBtnAC)) {
			this.mCalculatorModel.clearMathExpression();
		}
		else if(btn.equals(this.mBtnDel)) {
			this.mCalculatorModel.deleteLastCharacter();
		}		
	}
	
	public void activateController() {
	}

	public void deactivateController() {
	}
	
	public void initialize(RomanCalculator romanCalculator) {
		this.mContext = romanCalculator;

		this.mBtnAC = (Button) this.mContext.findViewById(R.id.btnAC);
		this.mBtnAC.setOnClickListener(this);
		this.mBtnAC.setText("AC");
		this.mBtnAC.getBackground().setColorFilter(0xCCE3170D, PorterDuff.Mode.MULTIPLY);
		
		this.mBtnDel = (Button) this.mContext.findViewById(R.id.btnDel);		
		this.mBtnDel.setOnClickListener(this);		
		this.mBtnDel.setText("Del");
		this.mBtnDel.getBackground().setColorFilter(0xCCE3170D, PorterDuff.Mode.MULTIPLY);

	}	
	private RomanCalculator mContext = null;
	private Button mBtnAC = null;	
	private Button mBtnDel = null;
	private CalculatorModel mCalculatorModel = null;
}
