package com.mcordova.android.solutions.romancalc.model;

import java.util.List;
import java.util.Vector;

import android.util.Log;

/**
 *
 * @author mcordova
 */
public class RomanNumeralsConverter implements IConverter{

    private static RomanNumeralsConverter INSTANCE = null;
    public static RomanNumeralsConverter getInstance() {
        if(RomanNumeralsConverter.INSTANCE == null) {
            RomanNumeralsConverter.INSTANCE = new RomanNumeralsConverter();
        }
        return RomanNumeralsConverter.INSTANCE;
    }
    
    private RomanNumeralsConverter() {

    }

    public boolean validateRomanNumber(String input){
    	boolean retval = false;
    	if(input.matches(RomanNumeralsConverter.getRomanNumeralsMatcherRegex()) == true) {
    		retval = this.isRomanNumeralValid(input);
    	}
    	return retval;
    }
    
    public double convertToDouble(String input) {
    	double doubleValue = 0;
        Vector<RomanNumeralValues> vector = this.parseToArray(input);

        for (RomanNumeralValues romanNumeral : vector) {
            doubleValue += romanNumeral.getArabicValue();
        }

        return doubleValue;
    }

    public String convertFromDouble(double input) {
        double doubleValue = Math.abs(input);
        String stringValue = "";
        if(input < 0) {
        	stringValue = "-";
        }

        for(RomanNumeralValues romanNumeral : RomanNumeralValues.values()) {
            while (doubleValue >= romanNumeral.getArabicValue()) {
                stringValue += romanNumeral.toString();
                doubleValue -= romanNumeral.getArabicValue();
                
                Log.v("convertFromDouble", "_______________________________");
                Log.v("convertFromDouble", "stringValue: " + stringValue);
                Log.v("convertFromDouble", "doubleValue: " + doubleValue);
                Log.v("convertFromDouble", "romanNumeral: " + romanNumeral.toString());
                
                
            }

            if(doubleValue <= 0) {
                break;
            }
        }

        if(doubleValue >= (RomanNumeralValues.u.getArabicValue() / 2)) {
        	stringValue += RomanNumeralValues.u.toString();
        }
        
        return stringValue;
    }

    // TODO: this method works but looks too complicated. refactor to make it simpler and
    //      more readable.
    public Vector<RomanNumeralValues> getNextValidNumeral(String input) {
        Vector<RomanNumeralValues> vector = this.parseToArray(input);
        System.out.println(vector);

        RomanNumeralValues lastElement = vector.lastElement();
        Vector<RomanNumeralValues> validValues = new Vector<RomanNumeralValues>();

        if(lastElement.toString().length() == 1) {
            boolean repeatedThrice = false;
            boolean repeatedTwice = false;

            List<RomanNumeralValues> subVector2 = null;
            List<RomanNumeralValues> subVector3 = null;

            // check if last number is repeated twice or thrice or not at all
            if(vector.size() >= 2) {
                subVector2 = vector.subList(vector.size() - 2, vector.size());
                if((subVector2.get(0) == subVector2.get(1))) {
                    repeatedTwice = true;
                }

                if(vector.size() >= 3) {
                    subVector3 = vector.subList(vector.size() - 3, vector.size());
                    if((subVector3.get(0) == subVector3.get(1)) && (subVector3.get(0) == subVector3.get(2))) {
                        repeatedThrice = true;
                    }
                }
            }

            // check if value is a power of 10
            if(( (!lastElement.equals(RomanNumeralValues.m)) &&
                    Math.log10((double) lastElement.getArabicValue())%1 == 0)) {
                System.out.println("power of 10 = " + Math.log10((double) lastElement.getArabicValue()));

                // if value is already repeated twice, subtraction rule no longer applies
                if(repeatedTwice == false) {
                    RomanNumeralValues tmpValue1 = this.getByArabicValue(lastElement.getArabicValue() * 5);
                    RomanNumeralValues tmpValue2 = this.getByArabicValue(lastElement.getArabicValue() * 10);

                    if(this.isRomanNumeralValid(input + tmpValue1.toString())) {
                        this.addToWhitelist(validValues, tmpValue1);
                    }

                    if(this.isRomanNumeralValid(input + tmpValue2.toString())) {
                        this.addToWhitelist(validValues, tmpValue2);
                    }
                }
            }

            // same value can only be repeated 3 times
            if(repeatedThrice == false) {
                this.addToWhitelist(validValues, lastElement);
            }
        }

        // if subtraction value
        if(lastElement.toString().length() == 2) {
            lastElement = RomanNumeralValues.valueOf(lastElement.toString().substring(0, 1));
        }

        // numerals lesser than last element are valid
        for(RomanNumeralValues romanNumeral : RomanNumeralValues.values()) {
            if(romanNumeral.getArabicValue() < lastElement.getArabicValue()) {
                this.addToWhitelist(validValues, romanNumeral);
            }
        }

        return validValues;
    }

    // NOTE: brute force checking if roman numeral string is valid. should only be used sparingly
    private boolean isRomanNumeralValid(String value) {
    	boolean retval = false;
    	String input = value;

    	// NOTE: equivalent to Math.abs on RomanNumerals. this simplifies the checking.
    	if(input.startsWith("-")) {
    		input = input.substring(1, input.length());
    	}
    	
    	double arabicValue = this.convertToDouble(input);
    	
    	// NOTE: account for roundoff errors. some of the fractions do not add up to whole numbers
    	//	as some of them are repeating decimals. account for such errors through a tolerance value 
    	//if(Math.abs(arabicValue - this.convertToDouble(input)) <= RomanNumeralValues.u.getArabicValue()) {    	
    	if(Math.abs(arabicValue - this.convertToDouble(input)) <= 0.1) {    		
    		retval = input.equals(this.convertFromDouble(this.convertToDouble(input)));
    	}

    	return retval;
    }

    private RomanNumeralValues getByArabicValue(double value){
        RomanNumeralValues retval = RomanNumeralValues.I;

        for(RomanNumeralValues romanNumeral : RomanNumeralValues.values()) {
            if(romanNumeral.getArabicValue() == value) {
                retval = romanNumeral;
            }
        }
        
        return retval;
    }

    private void addToWhitelist(Vector<RomanNumeralValues> validValues, RomanNumeralValues romanNumeral) {
        if(romanNumeral.toString().length() == 1) {
            validValues.add(romanNumeral);
        }
    }

    // NOTE: regex parsing might be a more efficient/accurate way of chopping up input.
    // TODO: experiment with regex parsing. see CalculatorCore method of parsing.
    private Vector<RomanNumeralValues> parseToArray(String input) {
        Vector<RomanNumeralValues> retval = new Vector<RomanNumeralValues>();
        for(RomanNumeralValues romanNumeral : RomanNumeralValues.values()) {
            int romanValueLength = romanNumeral.toString().length();
            do {
                if(input.length() >= romanValueLength) {
                    String tmpValue = input.substring(0,romanValueLength);
                    if(tmpValue.equals(romanNumeral.toString())) {
                        retval.add(romanNumeral);
                        input = input.substring(romanValueLength, input.length());
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }while(true);
        }
        Log.v("parseToArray", retval.toString());
        return retval;
    }
    
    private static String ROMAN_NUMERALS_MATCHER_REGEX = null;
    private static String getRomanNumeralsMatcherRegex(){
        if(RomanNumeralsConverter.ROMAN_NUMERALS_MATCHER_REGEX == null) {
        	String regex = "[-";
        	
        	for(RomanNumeralValues romanNumeral : RomanNumeralValues.values()) {
        		romanNumeral.toString();
        		if(romanNumeral.toString().length() == 1) {
        			regex = regex + romanNumeral.toString();
        		}
        	}
        	
        	regex = regex + "]+";
        	RomanNumeralsConverter.ROMAN_NUMERALS_MATCHER_REGEX = regex;
        }
        return RomanNumeralsConverter.ROMAN_NUMERALS_MATCHER_REGEX;
    }    
}
