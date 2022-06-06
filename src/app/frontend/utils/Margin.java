package app.frontend.utils;

import java.awt.Insets;

public enum Margin {
    EMPTY(0, 0, 0, 0);

    private Insets inset;

    private Margin(int top, int left, int bottom, int right) {
        this.inset = new Insets(top, left, bottom, right);
    }

    public Insets get() {
        return this.inset;
    }
}