import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Borrower> borrowers;

    public Library() {
        this.books = new ArrayList<>();
        this.borrowers = new ArrayList<>();
    }

    // ---------- Bulk add/get (used by Main for loading/saving) ----------

    public void addAllBooks(List<Book> booksToAdd) {
        books.addAll(booksToAdd);
    }

    public void addAllBorrowers(List<Borrower> borrowersToAdd) {
        borrowers.addAll(borrowersToAdd);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Borrower> getAllBorrowers() {
        return new ArrayList<>(borrowers);
    }

    // ---------- Core operations ----------

    public void addBook(Book book) {
        books.add(book);
    }

    public void addBorrower(Borrower borrower) {
        borrowers.add(borrower);
    }

    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.isbn().equals(isbn)) {
                return book;
            }
        }
        throw new BookNotFoundException("No book found with ISBN: " + isbn);
    }

    public Borrower findBorrowerById(String borrowerId) {
        for (Borrower borrower : borrowers) {
            if (borrower.getBorrowerId().equals(borrowerId)) {
                return borrower;
            }
        }
        throw new BorrowerNotFoundException("No borrower found with ID: " + borrowerId);
    }

    // Borrow / Return (object-level locking on the specific book) ----------

    public void borrowBook(String isbn, String borrowerId, String borrowDate) {
        Book book = findBookByISBN(isbn);
        Borrower borrower = findBorrowerById(borrowerId);

        synchronized (book) {
            if (!book.isAvailable()) {
                throw new BookNotAvailableException(
                    "Book is not available: " + book.getTitle());
            }
            book.setAvailable(false);
        }

        borrower.addBorrowedBook(isbn, borrowDate);
    }

    public void returnBook(String isbn, String borrowerId) {
        Book book = findBookByISBN(isbn);
        Borrower borrower = findBorrowerById(borrowerId);

        synchronized (book) {
            book.setAvailable(true);
        }

        borrower.returnBook(isbn);
    }

}