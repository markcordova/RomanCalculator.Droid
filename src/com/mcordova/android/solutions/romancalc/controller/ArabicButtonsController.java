package com.mcordova.android.solutions.romancalc.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mcordova.android.solutions.romancalc.RomanCalculator;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.solutions.android.romancalc.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ArabicButtonsController implements OnClickListener, ICalculatorController {
	private static ArabicButtonsController INSTANCE = null;
	public static ArabicButtonsController getInstance() {
		if(ArabicButtonsController.INSTANCE == null) {
			ArabicButtonsController.INSTANCE = new ArabicButtonsController();
		}		
		return ArabicButtonsController.INSTANCE;
	}
	
	private ArabicButtonsController() {
		this.mCalculatorModel = CalculatorModel.getInstance();
	}
	
	
	public void onClick(View view) {		
		Button btn = (Button) view;
		String num = this.buttons.get((Button) btn);
		if(num != null) {
			this.mCalculatorModel.appendArabicNumeral(num);
		}
	}

	public void activateController() {
    	for(Entry<Button, String> entry : this.buttons.entrySet()) {
    		entry.getKey().setOnClickListener(this);
    		entry.getKey().setText(entry.getValue().toString());
    		if(entry.getValue().toString().equals("")) {
    			entry.getKey().setEnabled(false);
    		}
    		else {
    			entry.getKey().setEnabled(true);
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
		this.buttons = new HashMap<Button, String>();
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey01), "");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey02), "0");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey03), ".");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey04), "1");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey05), "2");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey06), "3");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey07), "4");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey08), "5");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey09), "6");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey10), "7");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey11), "8");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey12), "9");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey13), "");
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey14), "");		
		this.buttons.put((Button) this.mContext.findViewById(R.id.btnKey15), "");
	}	
	private RomanCalculator mContext = null;
	private HashMap<Button, String> buttons = null;	
	private CalculatorModel mCalculatorModel = null;
}
