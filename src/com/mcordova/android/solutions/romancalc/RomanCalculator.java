package com.mcordova.android.solutions.romancalc;

import com.mcordova.android.solutions.romancalc.controller.ArabicButtonsController;
import com.mcordova.android.solutions.romancalc.controller.ClearButtonsController;
import com.mcordova.android.solutions.romancalc.controller.DisplayController;
import com.mcordova.android.solutions.romancalc.controller.EqualsButtonController;
import com.mcordova.android.solutions.romancalc.controller.OperatorButtonsController;
import com.mcordova.android.solutions.romancalc.controller.RomanButtonsController;
import com.mcordova.android.solutions.romancalc.model.CalculatorModel;
import com.mcordova.solutions.android.romancalc.R;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RomanCalculator extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        
        this.mCalculatorModel = CalculatorModel.getInstance();
        
        this.btnModeSwitcher = (Button) this.findViewById(R.id.btnModeSwitcher);
        this.btnModeSwitcher.setOnClickListener(new ModeSwitcherListener());
        this.btnModeSwitcher.getBackground().setColorFilter(0xCC007FFF, PorterDuff.Mode.MULTIPLY);

        this.mClearButtonsController = ClearButtonsController.getInstance();
        this.mOperatorsController = OperatorButtonsController.getInstance();
        this.mEqualsButtonController = EqualsButtonController.getInstance();
        this.mArabicButtonsController = ArabicButtonsController.getInstance();
        this.mRomanButtonsController = RomanButtonsController.getInstance();
        this.mDisplayController = DisplayController.getInstance();
        
        this.mClearButtonsController.initialize(this);
        this.mOperatorsController.initialize(this);
        this.mEqualsButtonController.initialize(this);
        this.mArabicButtonsController.initialize(this);        
        this.mRomanButtonsController.initialize(this);        
        this.mDisplayController.initialize(this);
        
        this.toggleCalculatorMode();
    }
    
    private class ModeSwitcherListener implements OnClickListener {
		public void onClick(View view) {
			Log.v("ModeSwitcherListener","onClick");
			toggleCalculatorMode();
		}    			
    }
    
    private void toggleCalculatorMode() {
    	switch(this.mCalculatorModel.getCalculatorMode()) {
	    	case BASIC_MODE :
	    		this.mArabicButtonsController.deactivateController();
	    		this.mRomanButtonsController.activateController();
	    		this.mCalculatorModel.switchMode(CalculatorModel.CalculatorMode.ROMAN_MODE);
	    		this.btnModeSwitcher.setText(R.string.switch_to_basic_calculator);
	    		break;
	    	case ROMAN_MODE :
	    		this.mRomanButtonsController.deactivateController();
	    		this.mArabicButtonsController.activateController();
	    		this.mCalculatorModel.switchMode(CalculatorModel.CalculatorMode.BASIC_MODE);
	    		this.btnModeSwitcher.setText(R.string.switch_to_roman_calculator);
	    	default:
    	}
    }
 
    private Button btnModeSwitcher = null;
    
    private DisplayController mDisplayController = null;
    private ArabicButtonsController mArabicButtonsController = null;
    private RomanButtonsController mRomanButtonsController = null;    
    private OperatorButtonsController mOperatorsController = null;
    private EqualsButtonController mEqualsButtonController = null;
    private ClearButtonsController mClearButtonsController = null;     
    
    private CalculatorModel mCalculatorModel = null;
    
}