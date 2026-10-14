package com.femzyk.library.core;

/**
 * LibraryItem - the generic base class for every item the catalog stores.
 *
 * CS 1103-01, Programming Assignment Unit 6. The class is generic: the
 * type parameter T stands for the type-specific details payload, so
 * Book uses LibraryItem<String> (genre text) while Dvd uses
 * LibraryItem<Integer> (duration in minutes). The shared attributes the
 * assignment requires - title, author and itemID - live here, protected
 * by validation in the constructor.
 *
 * @param <T> the type of the item-specific details (e.g. String genre)
 */
public class LibraryItem<T> {

    /** Unique identifier of the item (immutable, validated). */
    private final String itemID;

    /** Title of the item. */
    private String title;

    /** Author or creator of the item. */
    private String author;

    /** Type-specific details (genre, duration, frequency, ...). */
    private T details;

    /**
     * Creates a validated library item.
     *
     * @param itemID  unique identifier, not empty
     * @param title   title, not empty
     * @param author  author or creator, not empty
     * @param details type-specific payload, not null
     * @throws IllegalArgumentException if any argument is invalid
     */
    public LibraryItem(String itemID, String title, String author, T details) {
        if (itemID == null || itemID.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be empty.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        if (details == null) {
            throw new IllegalArgumentException("Item details cannot be missing.");
        }
        this.itemID = itemID.trim().toUpperCase();
        this.title = title.trim();
        this.author = author.trim();
        this.details = details;
    }

    /** Returns the unique item ID. */
    public String getItemID() {
        return itemID;
    }

    /** Returns the title. */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title.
     *
     * @param title new title, not empty
     * @throws IllegalArgumentException if the title is empty
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title.trim();
    }

    /** Returns the author or creator. */
    public String getAuthor() {
        return author;
    }

    /**
     * Updates the author.
     *
     * @param author new author, not empty
     * @throws IllegalArgumentException if the author is empty
     */
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        this.author = author.trim();
    }

    /** Returns the type-specific details payload. */
    public T getDetails() {
        return details;
    }

    /**
     * Updates the type-specific details.
     *
     * @param details new payload, not null
     * @throws IllegalArgumentException if the details are null
     */
    public void setDetails(T details) {
        if (details == null) {
            throw new IllegalArgumentException("Item details cannot be missing.");
        }
        this.details = details;
    }

    /** Returns a readable one-line description of the item. */
    @Override
    public String toString() {
        return String.format("[%-6s] %-28s by %-18s (%s)", itemID, title, author, details);
    }
}
