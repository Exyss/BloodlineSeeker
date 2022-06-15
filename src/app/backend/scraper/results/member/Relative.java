package app.backend.scraper.results.member;
/**
 * The relatives that can be used.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 *
 */
public enum Relative {
    
    /**
     * The CHILD relative.
     */
    CHILD,
    
    /**
     * The PARENTS relative.
     */
    PARENT,
    
    /**
     * The SPOUSE relative.
     */
    SPOUSE,
    
    /**
     * The INVALID relative.
     */
    INVALID;

    /**
     * Match the string given as parameter with the correct relative.
     * @param field the String containing a certain word.
     * @return the Relative that matches with the field.
     */
    public static Relative matchRelative(String field) {
        if (field.contains("Figli")) {
            return CHILD;
        } else if (field.contains("Madre") || field.contains("Padre")) {
            return PARENT;
        } else if (field.contains("Consort") || field.contains("Coniug")) {
            return SPOUSE;
        } else {
            return INVALID;
        }
    }
}