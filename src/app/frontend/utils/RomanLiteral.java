package app.frontend.utils;

/**
 * The Roman literals that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum RomanLiteral {
	/**
	 * The 400 Roman literal
	 */
    CD(400),
    
    /**
	 * The 100 Roman literal
	 */
    C(100),
    
    /**
	 * The 90 Roman literal
	 */
    XC(90),
    
    /**
	 * The 50 Roman literal
	 */
    L(50),
    
    /**
	 * The 40 Roman literal
	 */
    XL(40),
    
    /**
	 * The 10 Roman literal
	 */
    X(10),
    
    /**
	 * The 9 Roman literal
	 */
    IX(9),
    
    /**
	 * The 5 Roman literal
	 */
    V(5),
    
    /**
	 * The 4 Roman literal
	 */
    IV(4),
    
    /**
	 * The 1 Roman literal
	 */
    I(1);
    
	/**
	 * A decimal number.
	 */
    private int decimal;
    
    /**
     * Initializes the decimal value using the parameter.
     * @param decimal the new decimal value.
     */
    private RomanLiteral(int decimal) {
        this.decimal = decimal;
    }
    
    /**
     * 
     * @return the decimal value.
     */
    public int get() {
        return decimal;
    }

    /**
     * Converts the decimal value in the Roman literals
     * @param decimal the decimal value to convert.
     * @return the Roman literals.
     */
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