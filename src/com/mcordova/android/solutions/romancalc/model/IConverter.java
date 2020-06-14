package com.mcordova.android.solutions.romancalc.model;

public interface IConverter {
    public double convertToDouble(String input);
    public String convertFromDouble(double input);
}
