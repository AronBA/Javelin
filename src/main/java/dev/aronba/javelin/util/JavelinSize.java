package dev.aronba.javelin.util;

import java.awt.*;

/**
 * The JavelinSize class provides pre-defined Dimension constants representing
 * different sizes for the Javelin application window.
 */
public class JavelinSize {

    private JavelinSize(){
        // Private constructor to prevent instantiation; all methods are static.
    }

    /**
     * The normal size for the Javelin application window.
     */
    public static final Dimension NORMAL = new Dimension(1000, 1000);

    /**
     * A smaller size for the Javelin application window.
     */
    public static final Dimension SMALL = new Dimension(400, 400);
}
