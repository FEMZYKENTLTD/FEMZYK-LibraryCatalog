package com.femzyk.library.core;

/**
 * Dvd - a library item whose generic details payload is the duration.
 *
 * CS 1103-01, Programming Assignment Unit 6. The details type is Integer
 * here, which proves the catalog works with more than one type argument.
 */
public class Dvd extends LibraryItem<Integer> {

    /**
     * Creates a validated DVD.
     *
     * @param itemID   unique identifier
     * @param title    title
     * @param author   director or creator
     * @param duration duration in minutes (the generic details payload)
     */
    public Dvd(String itemID, String title, String author, int duration) {
        super(itemID, title, author, duration);
    }

    /** Returns a DVD-specific description, reusing the generic formatting. */
    @Override
    public String toString() {
        return "DVD     " + super.toString();
    }
}
