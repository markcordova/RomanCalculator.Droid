package com.mcordova.android.solutions.romancalc.model;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 * @author Mark Philip Cordova
 */
public class CalculatorCore {
    private static CalculatorCore INSTANCE = null;
    public static CalculatorCore getInstance() {
        if(CalculatorCore.INSTANCE == null) {
            CalculatorCore.INSTANCE = new CalculatorCore();
        }
        return CalculatorCore.INSTANCE;
    }

    private CalculatorCore() {
    }

    public double evaluate(String input) throws CalculatorException {
        String[] tokens = this.tokenizeInput(input);
        Stack<String> postfixQueue = this.convertToPostfix(tokens);
        Double answer = this.evaluatePostfix(postfixQueue);

        return answer;
    }

    public String[] tokenizeInput(String input) {
        input = "(" + input + ")";
        String tokens[] = input.split(CalculatorCore.getSplitRegex());
        for(int i = 0; i < tokens.length; i++) {
            String token = tokens[i];            
            if(token.equals("(")){
            	if(i > 0){
                    String previousToken = tokens[i - 1];
                    if(this.isNumber(previousToken) == true) {
                    	tokens = this.insertElement(tokens, "*", i);
                    }
                }
            }
            else if(token.equals(")")){
            	if(i <= tokens.length - 2){
            		String nextToken = tokens[i + 1];
                    if(this.isNumber(nextToken) == true) {
                    	tokens = this.insertElement(tokens, "*", i);
                    }            		
            	}
            }
        }
        
        for(int i = 0; i < tokens.length; i++) {
            String token = tokens[i];            
            if(token.equals("-")){
                if(i >= tokens.length - 1) {
                    // NOTE: error condition! "-" can't appear at the end of expression!
                }
                else {
                    String previousToken = tokens[i - 1];                    
                    if(previousToken.equals("(") || this.isOperator(previousToken)){                        
                        tokens[i] = "_";
                    }
                }
            }            
        }
        
        return tokens;
    }

    public void validateTokens(String[] tokens) throws CalculatorException {
    	int numParenthesis = 0;
    	
        for(int i = 0; i < tokens.length; i++) {
        	if(tokens[i].length() == 0) {
        		// NOTE: ignore empty tokens? or remove them completely?
        	}
        	else if(this.isValidToken(tokens[i]) == false) {
		    	throw(new CalculatorException(CalculatorException.INPUT_NOT_RECOGNIZED, tokens[i]));			    	           	       		
        	}
        	
        	if(tokens[i].equals("(")) {
        		numParenthesis++;
        	}
        	else if(tokens[i].equals(")")) {
        		numParenthesis--;
        	}
        }
            
        if(numParenthesis != 0) {
	    	throw(new CalculatorException(CalculatorException.UNMATCHED_PARENTHESIS, ""));        	
        }
    }
    
    public boolean isValidToken(String token){
    	System.out.println();
    	boolean retval = false;
    	if(this.isOperator(token) || this.isNumber(token) || this.isParenthesis(token) ) {
    		retval = true;
    	}
    	
    	return retval;
    }

    private String[] insertElement(String original[], String element, int index) {
		int length = original.length;
		String destination[] = new String[length + 1];
		System.arraycopy(original, 0, destination, 0, index);
		destination[index] = element;
		System.arraycopy(original, index, destination, index + 1, length - index);
		return destination;
    }    
    
    public Stack<String> convertToPostfix(String[] tokens) throws CalculatorException {
        Stack<String> tokenStack = new Stack<String>();
        Stack<String> postfixQueue = new Stack<String>();

        for(String s : tokens){
            if(s.equals("")){// || s.contains(" ")) {
                // NOTE: ignore empty tokens and whitespace
            }
            else if(this.isOperator(s)) {                
                CalculatorOperations operation = this.getOperation(s);
                if(operation.isBinaryOperator() ==  false) {
                    if(operation.isUnaryPrefix() == true) {
                        tokenStack.push(s);
                    }
                    else {
                        postfixQueue.push(s);
                    }
                }
                else {
                    ListIterator<String> stackIterator = tokenStack.listIterator(tokenStack.size());
                    while(stackIterator.hasPrevious() == true) {
                        String op = (String) stackIterator.previous();
                        if(this.isOperator(op)) {
                            CalculatorOperations operationB = this.getOperation(op);
                            if(operationB.getPrecedence() > operation.getPrecedence()) {
                                postfixQueue.push(op);
                                stackIterator.remove();
                            }
                            else if(operationB.getPrecedence() == operation.getPrecedence()) {
                                if(operation.isLeftAssociative() == true) {
                                    postfixQueue.push(op);
                                    stackIterator.remove();
                                }
                            }
                        }
                        else {
                            break;
                        }
                    }
                    tokenStack.push(s);
                }
            }
            else if (s.equals(")")) {
                while(! tokenStack.isEmpty()) {
                    String op = tokenStack.pop();
                    if(! op.equals("(")) {
                        postfixQueue.push(op);
                    }
                    else {
                        break;
                    }
                }
            }
            else if (s.equals("(")) {
                tokenStack.push(s);
            }
            else if (this.isNumber(s) == true){
                postfixQueue.push(s);
            }
            else {
		    	throw(new CalculatorException(CalculatorException.INPUT_NOT_RECOGNIZED, s));			    	           	
            }
        }
        
        return postfixQueue;
    }

    public double evaluatePostfix(Stack<String> postfixQueue) throws CalculatorException  {
        double retval = 0;
        Stack<String> tokenStack = new Stack<String>();

        try {
			Collections.reverse(postfixQueue);
			while(! postfixQueue.isEmpty()) {
			    String s = postfixQueue.pop();
			    if(this.isOperator(s) == true) {
			        CalculatorOperations operation  = this.getOperation(s);
			        if(operation.isBinaryOperator() == true) {
			            Double Y = this.getNumber(tokenStack.pop());
			            Double X = this.getNumber(tokenStack.pop());
			            tokenStack.push("" + operation.evaluate(X, Y));
			        }
			        else {
			            Double X = this.getNumber(tokenStack.pop());
			            tokenStack.push("" + operation.evaluate(X, 0));
			        }
			    }
			    else if(this.isNumber(s) == true){
			        tokenStack.push(s);                
			    }
			    else {
			    	throw(new CalculatorException(CalculatorException.INPUT_NOT_RECOGNIZED, s));			    	
			    }
			}
			retval = this.getNumber(tokenStack.pop());
    	}catch (EmptyStackException e) {			
			System.out.println("EmptyStackException caught.");
            throw(new CalculatorException(CalculatorException.OPERATOR_INVALID_PARAMETERS,
                    "Empty Stack Exception"));
		}
        
        return retval;
    }

    public boolean isOperator(String token) {
        boolean retval = false;
        if(CalculatorOperations.OPERATOR_MAP.get(token) != null){
            retval = true;
        }

        return retval;
    }

    public boolean isParenthesis(String token) {
        boolean retval = false;
        if(token.equals("(") || token.equals(")")) {
        	retval = true;
        }
        return retval;    	
    }
    
    public boolean isNumber(String number) {    	
        boolean retval = true;
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException ex) {
            retval = false;
        }
        return retval;
    }

    private double getNumber(String number) throws CalculatorException {
        double retval = 0;
        if(this.isNumber(number) == true) {
            retval = Double.parseDouble(number);
        }
        else {
            throw(new CalculatorException(CalculatorException.NUMBER_FORMAT_ERROR, number));
        }

        return retval;
    }

    private CalculatorOperations getOperation(String token) throws CalculatorException {
        CalculatorOperations retval = (CalculatorOperations) CalculatorOperations.OPERATOR_MAP.get(token);
        if(retval == null) {
            throw(new CalculatorException(CalculatorException.INPUT_NOT_RECOGNIZED, token));
        }

        return retval;
    }

    private static String SPLIT_REGEX = null;
    private static String getSplitRegex(){
        if(CalculatorCore.SPLIT_REGEX == null) {
            for(String ops : CalculatorOperations.OPERATOR_MAP.keySet()) {
                if(ops.equals("_") || ops.equals(null)) {
                    continue;
                }

                if(ops.length() == 1) {
                    ops = "\\" + ops;
                }
                else {
                    ops = "" + ops;
                }

                if(CalculatorCore.SPLIT_REGEX != null) {
                   CalculatorCore.SPLIT_REGEX += "|";
                }
                else {
                    CalculatorCore.SPLIT_REGEX = "((?<=\\()|(?=\\())|((?<=\\))|(?=\\)))|((?<=\\_)|(?=\\_))" + "|";
                }

                CalculatorCore.SPLIT_REGEX += "((?<=" + ops + ")|(?=" + ops + "))";
            }
        }
        return CalculatorCore.SPLIT_REGEX;
    }
}

/* NOTES:
 *  - first attempt at a mathematical expression parser / calculator!
 *  - relies purely on CalculatorOperations enum for specific mathematical operations for ease of extensibility
 *  - based on shunting-yard algorithm described here: http://www.chris-j.co.uk/parsing.php
 */