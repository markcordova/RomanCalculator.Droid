package com.mcordova.android.solutions.romancalc.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.solutions.android.romancalc.R;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OperatorButtonsController implements OnClickListener, ICalculatorController  {
	private static OperatorButtonsController INSTANCE = null;
	public static OperatorButtonsController getInstance() {
		if(OperatorButtonsController.INSTANCE == null) {
			OperatorButtonsController.INSTANCE = new OperatorButtonsController();
		}		
		return OperatorButtonsController.INSTANCE;
	}
	
	private OperatorButtonsController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
	}
	
	public void onClick(View view) {
		Log.v("onClick", "" + this.buttons.get((Button) view).toString());
		Button btn = (Button) view;
		String op = this.buttons.get((Button) btn);
		if(op != null) {
			this.mCalculatorModel.appendOperator(op);
		}		
	}
	
	public void activateController() {
	}

	public void deactivateController() {
	}
	
	public void initialize(RomanCalculator romanCalculator) {
		this.mContext = romanCalculator;
		this.mapButtons();
	}
	
	private void mapButtons() {		
		this.buttons = new HashMap<Button, String>();
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnOpenParenthesis), "(");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnCloseParenthesis), ")");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnAdd), "+");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnSubract), "-");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnMultiply), "*");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnDivide), "/");
		
    	for(Entry<Button, String> entry : this.buttons.entrySet()) {
    		entry.getKey().setOnClickListener(this);
    		entry.getKey().setText(entry.getValue().toString());
    		entry.getKey().getBackground().setColorFilter(0xFFC0C0C0, PorterDuff.Mode.MULTIPLY);
    	}					
	}	
	private RomanCalculator mContext = null;
	private HashMap<Button, String> buttons = null;	
	private CalculatorModel mCalculatorModel = null;
}
