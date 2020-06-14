package com.mcordova.android.solutions.romancalc.model;

public enum RomanNumeralValues {
    m(1000000),
    cm(900000),
    d(500000),
    cd(400000),
    c(100000),
    xc(90000),
    l(50000),
    xl(40000),
    x(10000),
    Mx(9000),
    v(5000),
    Mv(4000),    
    M(1000),
    CM(900),
    D(500),
    CD(400),
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1),
    Suuuuu(0.91666),           
    Suuuu(0.83333),          
    Suuu(0.75),
    Suu(0.66666),        
    Su(0.58333),       
    S(0.5),
    uuuuu(0.41666),          
    uuuu(0.33333),         
    uuu(0.25),
    uu(0.16666),       
    u(0.08333);    
    
    RomanNumeralValues(double value) {
        this.mArabicValue = value;
    }

    public double getArabicValue() {
        return this.mArabicValue;
    }

    private double mArabicValue;
}
