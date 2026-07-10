import java.util.List;
import java.util.Scanner;

public class Main {

    private static Library library = new Library();
    private static FileManager fileManager = new FileManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load any previously saved data before showing the menu,
        // so the program picks up where the last run left off
        library.addAllBooks(fileManager.loadBooks());
        library.addAllBorrowers(fileManager.loadBorrowers());

        printBanner();
        runMenuLoop();
    }

    private static void printBanner() {
        System.out.println("==========================================");
        System.out.println("      LIBRARY MANAGEMENT SYSTEM");
        System.out.println("==========================================");
    }

    private static void runMenuLoop() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> registerBorrower();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> viewBorrowerDetails();
                case 7 -> runConcurrencyDemo();
                case 0 -> {
                    saveAndExit();
                    running = false;
                }
                default -> System.out.println("Invalid option, try again.\n");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Register Borrower");
        System.out.println("4. Borrow Book");
        System.out.println("5. Return Book");
        System.out.println("6. View Borrower Details");
        System.out.println("7. Run Concurrency Demo");
        System.out.println("0. Save & Exit");
        System.out.print("Choose an option: ");
    }

    private static int readMenuChoice() {
        // Guard against non-numeric input (e.g. someone typing "banana")
        // so a bad input doesn't crash the whole program
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // falls into "Invalid option" in the switch above
        }
    }

    private static void saveAndExit() {
        fileManager.saveBooks(library.getAllBooks());
        fileManager.saveBorrowers(library.getAllBorrowers());
        System.out.println("Data save Goodbye and enjoy your day !");
    }

   private static void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Book book = new Book(title, author, isbn, year, genre);
        library.addBook(book);
        System.out.println("Book added successfully.\n");
    }

    private static void viewBooks() {
        List<Book> books = library.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books in the library yet.\n");
            return;
        }

        System.out.printf("%-25s %-20s %-15s %-6s %-15s %-10s%n",
                "TITLE", "AUTHOR", "ISBN", "YEAR", "GENRE", "AVAILABLE");

        for (Book book : books) {
            System.out.printf("%-25s %-20s %-15s %-6d %-15s %-10s%n",
                    book.getTitle(), book.author(), book.isbn(),
                    book.getYear(), book.getGenre(), book.isAvailable());
        }
        System.out.println();
    }

    private static void registerBorrower() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Borrower ID: ");
        String borrowerId = scanner.nextLine();

        Borrower borrower = new Borrower(name, borrowerId);
        library.addBorrower(borrower);
        System.out.println("Borrower registered successfully.\n");
    }

    private static void borrowBook() {
        System.out.print("Borrower ID: ");
        String borrowerId = scanner.nextLine();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            library.borrowBook(isbn, borrowerId, java.time.LocalDate.now().toString());
            System.out.println("Book borrowed successfully.\n");
        } catch (BookNotFoundException | BorrowerNotFoundException | BookNotAvailableException e) {
            System.out.println("Could not borrow book: " + e.getMessage() + "\n");
        }
    }

    private static void returnBook() {
        System.out.print("Borrower ID: ");
        String borrowerId = scanner.nextLine();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            library.returnBook(isbn, borrowerId);
            System.out.println("Book returned successfully.\n");
        } catch (BookNotFoundException | BorrowerNotFoundException e) {
            System.out.println("Could not return book: " + e.getMessage() + "\n");
        }
    }

    private static void viewBorrowerDetails() {
        System.out.print("Borrower ID: ");
        String borrowerId = scanner.nextLine();

        try {
            Borrower borrower = library.findBorrowerById(borrowerId);
            System.out.println("Name: " + borrower.getName());
            System.out.println("Borrowed ISBNs: " + borrower.getBorrowedISBNs());
            System.out.println("Borrow Dates: " + borrower.getBorrowDates());
            System.out.println();
        } catch (BorrowerNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private static void runConcurrencyDemo() {
        System.out.println("Concurrency demo coming next step.\n");
    } 
}