package com.femzyk.library.core;

/**
 * Magazine - a library item whose generic details payload is the
 * publication frequency.
 *
 * CS 1103-01, Programming Assignment Unit 6. A third type argument for
 * the same generic base class and the same generic catalog.
 */
public class Magazine extends LibraryItem<String> {

    /**
     * Creates a validated magazine.
     *
     * @param itemID    unique identifier
     * @param title     title
     * @param author    publisher or editor
     * @param frequency publication frequency (e.g. "monthly")
     */
    public Magazine(String itemID, String title, String author, String frequency) {
        super(itemID, title, author, frequency);
    }

    /** Returns a magazine-specific description, reusing the generic formatting. */
    @Override
    public String toString() {
        return "MAGAZINE " + super.toString();
    }
}
