package com.femzyk.library.core;

/**
 * Book - a library item whose generic details payload is the genre.
 *
 * CS 1103-01, Programming Assignment Unit 6. Because the details type is
 * chosen per subclass, the same generic LibraryItem base class serves
 * books (String genre), DVDs (Integer minutes) and magazines.
 */
public class Book extends LibraryItem<String> {

    /**
     * Creates a validated book.
     *
     * @param itemID unique identifier
     * @param title  title
     * @param author author
     * @param genre  the type-specific details payload
     */
    public Book(String itemID, String title, String author, String genre) {
        super(itemID, title, author, genre);
    }

    /** Returns a book-specific description, reusing the generic formatting. */
    @Override
    public String toString() {
        return "BOOK    " + super.toString();
    }
}
