package com.femzyk.library.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Catalog - the generic library catalog for any LibraryItem type.
 *
 * CS 1103-01, Programming Assignment Unit 6. One class written once
 * manages books, DVDs and magazines: the type parameter T is bounded by
 * LibraryItem&lt;?&gt;, so the catalog can call the shared operations of
 * any item (such as getItemID) while remaining fully type safe for the
 * concrete type it stores.
 *
 * Error handling: add() and removeById() throw the checked
 * LibraryException when a duplicate ID or a missing item is involved, so
 * callers must decide how to report the problem.
 *
 * @param <T> the concrete item type this catalog instance stores
 */
public class Catalog<T extends LibraryItem<?>> {

    /** Type-safe storage: one item per ID. */
    private final Map<String, T> items = new HashMap<>();

    /**
     * Adds a new item to the catalog.
     *
     * @param item the item to add, not null
     * @return the added item
     * @throws LibraryException    if an item with the same ID already exists
     * @throws IllegalArgumentException if the item is null
     */
    public T add(T item) throws LibraryException {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item.");
        }
        if (items.containsKey(item.getItemID())) {
            throw new LibraryException("An item with ID '" + item.getItemID()
                    + "' already exists in the catalog.");
        }
        items.put(item.getItemID(), item);
        return item;
    }

    /**
     * Removes an item by ID.
     *
     * @param itemID the ID of the item to remove
     * @return the removed item
     * @throws LibraryException if no item with that ID exists
     */
    public T removeById(String itemID) throws LibraryException {
        T removed = items.remove(normalize(itemID));
        if (removed == null) {
            throw new LibraryException("Cannot remove: no item with ID '"
                    + normalize(itemID) + "' exists in the catalog.");
        }
        return removed;
    }

    /**
     * Retrieves an item by ID.
     *
     * @param itemID the ID to look for
     * @return an Optional holding the item, or empty when not found
     */
    public Optional<T> findById(String itemID) {
        return Optional.ofNullable(items.get(normalize(itemID)));
    }

    /**
     * Returns every item, sorted by ID so the view is stable.
     *
     * @return a read-friendly list of all stored items
     */
    public List<T> getAll() {
        List<T> all = new ArrayList<>(items.values());
        all.sort(Comparator.comparing(LibraryItem::getItemID));
        return all;
    }

    /** Returns how many items the catalog currently stores. */
    public int count() {
        return items.size();
    }

    /** Uppercases and trims an ID so lookups are case-insensitive. */
    private String normalize(String itemID) {
        return itemID == null ? "" : itemID.trim().toUpperCase();
    }
}
