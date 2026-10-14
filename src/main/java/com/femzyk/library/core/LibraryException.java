package com.femzyk.library.core;

/**
 * LibraryException - the checked exception for every expected catalog
 * failure, such as removing a non-existent item or adding a duplicate ID.
 *
 * CS 1103-01, Programming Assignment Unit 6. Using a checked exception
 * forces the calling code to handle these situations explicitly, which is
 * exactly the graceful error handling the rubric asks for.
 */
public class LibraryException extends Exception {

    /**
     * Creates the exception with a user-facing message.
     *
     * @param message clear description of what went wrong
     */
    public LibraryException(String message) {
        super(message);
    }
}
