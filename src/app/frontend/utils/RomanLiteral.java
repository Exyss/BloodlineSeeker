package app.frontend.utils;

public enum RomanLiteral {
    CD(400),
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1);
    
    private int decimal;
    
    private RomanLiteral(int decimal) {
        this.decimal = decimal;
    }
    
    public int get() {
        return decimal;
    }

    public static String decimalToRoman(int decimal) {
        StringBuilder roman = new StringBuilder();

        for (RomanLiteral romanLiteral : RomanLiteral.values()) {
            for(int value = romanLiteral.get(); decimal >= value; decimal -= value) {
                roman.append(romanLiteral.name());
            }
        }
        return roman.toString();
    }
}