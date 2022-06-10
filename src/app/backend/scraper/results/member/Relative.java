package app.backend.scraper.results.member;

public enum Relative {
    CHILD,
    PARENT,
    SPOUSE,
    INVALID;

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