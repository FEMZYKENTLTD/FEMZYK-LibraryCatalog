package com.femzyk.library.console;

import java.util.Scanner;

import com.femzyk.library.core.Book;
import com.femzyk.library.core.Catalog;
import com.femzyk.library.core.Dvd;
import com.femzyk.library.core.LibraryException;
import com.femzyk.library.core.LibraryItem;
import com.femzyk.library.core.Magazine;

/**
 * LibraryApp - command-line interface of the FEMZYK Library Catalog
 * (CS 1103-01, Programming Assignment Unit 6).
 *
 * One generic Catalog instance per item type (Catalog of Book,
 * Catalog of Dvd, Catalog of Magazine) is managed through the same
 * menu-driven loop. Every operation wraps the checked LibraryException
 * in clear, friendly messages, and all user input is validated with
 * re-prompting loops.
 */
public class LibraryApp {

    private static final String DIVIDER = "--------------------------------------------------";

    private final Catalog<Book> books = new Catalog<>();
    private final Catalog<Dvd> dvds = new Catalog<>();
    private final Catalog<Magazine> magazines = new Catalog<>();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Program entry point: starts the interactive menu.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new LibraryApp().run();
    }

    /** The interactive menu loop. */
    private void run() {
        System.out.println("==================================================");
        System.out.println("     FEMZYK LIBRARY CATALOG (generic classes)     ");
        System.out.println("==================================================");
        seedExamples();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addItem();
                case "2" -> removeItem();
                case "3" -> viewCatalog();
                case "4" -> findItem();
                case "5" -> {
                    running = false;
                    System.out.println("\nGoodbye! The catalog stored "
                            + (books.count() + dvds.count() + magazines.count())
                            + " item(s) in total.");
                }
                default -> System.out.println("Invalid choice: please enter a number from 1 to 5.");
            }
        }
        scanner.close();
    }

    /** Prints the main menu. */
    private void printMenu() {
        System.out.println();
        System.out.println("1. Add an item");
        System.out.println("2. Remove an item");
        System.out.println("3. View the whole catalog");
        System.out.println("4. Find an item by ID");
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    /** Menu option 1: creates an item of the chosen type and adds it. */
    private void addItem() {
        System.out.println("\nAdd an item - " + TYPE_PROMPT);
        int type = readItemType();
        if (type == -1) {
            return;
        }
        try {
            String id = promptNonEmpty("Item ID: ");
            String title = promptNonEmpty("Title: ");
            String author = promptNonEmpty("Author/Creator: ");
            switch (type) {
                case 1 -> {
                    String genre = promptNonEmpty("Genre: ");
                    Book book = new Book(id, title, author, genre);
                    books.add(book);
                    System.out.println("Added to the book catalog: " + book);
                }
                case 2 -> {
                    int minutes = promptPositiveInt("Duration in minutes: ");
                    Dvd dvd = new Dvd(id, title, author, minutes);
                    dvds.add(dvd);
                    System.out.println("Added to the DVD catalog: " + dvd);
                }
                default -> {
                    String frequency = promptNonEmpty("Publication frequency: ");
                    Magazine magazine = new Magazine(id, title, author, frequency);
                    magazines.add(magazine);
                    System.out.println("Added to the magazine catalog: " + magazine);
                }
            }
        } catch (LibraryException error) {
            System.out.println("Cannot add the item: " + error.getMessage());
        }
    }

    /** Menu option 2: removes an item, reporting missing IDs gracefully. */
    private void removeItem() {
        System.out.println("\nRemove an item - " + TYPE_PROMPT);
        int type = readItemType();
        if (type == -1) {
            return;
        }
        String id = promptNonEmpty("Item ID: ");
        try {
            LibraryItem<?> removed = switch (type) {
                case 1 -> books.removeById(id);
                case 2 -> dvds.removeById(id);
                default -> magazines.removeById(id);
            };
            System.out.println("Removed: " + removed);
        } catch (LibraryException error) {
            System.out.println(error.getMessage());
        }
    }

    /** Menu option 3: prints every catalog grouped by item type. */
    private void viewCatalog() {
        System.out.println("\n" + DIVIDER);
        printCatalog("Books     (Catalog<Book>)", books);
        printCatalog("DVDs      (Catalog<Dvd>)", dvds);
        printCatalog("Magazines (Catalog<Magazine>)", magazines);
        System.out.println(DIVIDER);
        System.out.println("Total items: "
                + (books.count() + dvds.count() + magazines.count()));
    }

    /** Prints one catalog section with a friendly empty state. */
    private void printCatalog(String label, Catalog<? extends LibraryItem<?>> catalog) {
        System.out.println(label + " - " + catalog.count() + " item(s):");
        var all = catalog.getAll();
        if (all.isEmpty()) {
            System.out.println("   (empty)");
        } else {
            for (LibraryItem<?> item : all) {
                System.out.println("   " + item);
            }
        }
    }

    /** Menu option 4: finds an item by ID in the chosen catalog. */
    private void findItem() {
        System.out.println("\nFind an item - " + TYPE_PROMPT);
        int type = readItemType();
        if (type == -1) {
            return;
        }
        String id = promptNonEmpty("Item ID: ");
        var found = switch (type) {
            case 1 -> books.findById(id);
            case 2 -> dvds.findById(id);
            default -> magazines.findById(id);
        };
        System.out.println(found.isPresent()
                ? "Found: " + found.get()
                : "No item with ID '" + id.trim().toUpperCase() + "' was found.");
    }

    private static final String TYPE_PROMPT = "1) Book  2) DVD  3) Magazine";

    /** Reads and validates the item type selection. */
    private int readItemType() {
        System.out.print(TYPE_PROMPT + ": ");
        String input = scanner.nextLine().trim();
        if (input.equals("1") || input.equals("2") || input.equals("3")) {
            return Integer.parseInt(input);
        }
        System.out.println("Invalid type: please enter 1, 2 or 3.");
        return -1;
    }

    /** Keeps prompting until the user enters a non-empty line. */
    private String promptNonEmpty(String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This field cannot be empty. Please try again.");
            }
        }
        return input;
    }

    /** Keeps prompting until the user enters a positive whole number. */
    private int promptPositiveInt(String prompt) {
        while (true) {
            String input = promptNonEmpty(prompt);
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException error) {
                System.out.println("Please enter a whole number, e.g. 96.");
            }
        }
    }

    /** Seeds a few items so the catalog is not empty on first view. */
    private void seedExamples() {
        try {
            books.add(new Book("B100", "Effective Java", "Joshua Bloch", "Programming"));
            dvds.add(new Dvd("D200", "Inception", "Christopher Nolan", 148));
            magazines.add(new Magazine("M300", "Java Monthly", "Code Press", "monthly"));
        } catch (LibraryException error) {
            System.out.println("Seeding failed: " + error.getMessage());
        }
    }
}
