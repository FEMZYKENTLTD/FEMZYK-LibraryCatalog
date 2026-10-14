package com.femzyk.library.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CatalogTest - comprehensive tests for the generic catalog and the
 * generic LibraryItem class (CS 1103-01, Programming Assignment Unit 6).
 *
 * The same test suite runs against catalogs of two different item types,
 * which is itself the main requirement: the generic catalog must work
 * seamlessly with different types of library items.
 */
class CatalogTest {

    private Catalog<Book> bookCatalog;
    private Catalog<Dvd> dvdCatalog;

    @BeforeEach
    void setUp() {
        bookCatalog = new Catalog<>();
        dvdCatalog = new Catalog<>();
    }

    @Test
    @DisplayName("add and findById work for books")
    void addAndFindBook() throws LibraryException {
        Book book = bookCatalog.add(new Book("B1", "Clean Code", "Robert Martin", "Programming"));
        assertEquals(1, bookCatalog.count());
        assertTrue(bookCatalog.findById("b1").isPresent());
        assertEquals("Clean Code", bookCatalog.findById("B1").get().getTitle());
        assertEquals(book, book);
        assertFalse(bookCatalog.findById("B1").isEmpty());
        assertNull(bookCatalog.findById("NOPE").orElse(null));
    }

    @Test
    @DisplayName("adding a duplicate ID throws LibraryException")
    void duplicateAddThrows() throws LibraryException {
        bookCatalog.add(new Book("B1", "Clean Code", "Robert Martin", "Programming"));
        LibraryException error = assertThrows(LibraryException.class,
                () -> bookCatalog.add(new Book("b1", "Another Book", "Someone", "Fiction")));
        assertTrue(error.getMessage().contains("already exists"));
        assertEquals(1, bookCatalog.count());
    }

    @Test
    @DisplayName("removeById removes an existing item and returns it")
    void removeExisting() throws LibraryException {
        bookCatalog.add(new Book("B1", "Clean Code", "Robert Martin", "Programming"));
        LibraryItem<?> removed = bookCatalog.removeById("b1");
        assertTrue(removed.getTitle().contains("Clean Code"));
        assertEquals(0, bookCatalog.count());
    }

    @Test
    @DisplayName("removing a non-existent item throws a clear LibraryException")
    void removeNonExistingThrows() {
        LibraryException error = assertThrows(LibraryException.class,
                () -> bookCatalog.removeById("B404"));
        assertTrue(error.getMessage().contains("Cannot remove"));
        assertTrue(error.getMessage().contains("B404"));
    }

    @Test
    @DisplayName("the same generic catalog class also stores DVDs with Integer details")
    void catalogWorksWithDifferentTypes() throws LibraryException {
        dvdCatalog.add(new Dvd("D1", "Inception", "Christopher Nolan", 148));
        assertTrue(dvdCatalog.findById("D1").isPresent());
        assertEquals(148, dvdCatalog.findById("D1").get().getDetails());
        assertEquals(1, dvdCatalog.count());
        assertTrue(dvdCatalog.findById("D1").get() instanceof Dvd);
    }

    @Test
    @DisplayName("getAll returns the items sorted by ID")
    void getAllIsSorted() throws LibraryException {
        bookCatalog.add(new Book("B3", "Third", "C Author", "Sci-Fi"));
        bookCatalog.add(new Book("B1", "First", "A Author", "Programming"));
        bookCatalog.add(new Book("B2", "Second", "B Author", "History"));
        assertEquals(3, bookCatalog.getAll().size());
        assertEquals("B1", bookCatalog.getAll().get(0).getItemID());
        assertEquals("B2", bookCatalog.getAll().get(1).getItemID());
        assertEquals("B3", bookCatalog.getAll().get(2).getItemID());
    }

    @Test
    @DisplayName("LibraryItem validation rejects invalid constructors")
    void itemValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("  ", "Title", "Author", "Genre"));
        assertThrows(IllegalArgumentException.class,
                () -> new Book("B1", "  ", "Author", "Genre"));
        assertThrows(IllegalArgumentException.class,
                () -> new Magazine("M1", "Title", "Publisher", null));
    }

    @Test
    @DisplayName("findById is case-insensitive")
    void findIsCaseInsensitive() throws LibraryException {
        bookCatalog.add(new Book("b7", "Lower Case ID", "An Author", "Drama"));
        assertTrue(bookCatalog.findById("B7").isPresent());
        assertTrue(bookCatalog.findById("b7").isPresent());
    }
}
