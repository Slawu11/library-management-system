import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
 // File names are constants because they never change during the program's run
 // Using text files (not serialization) so the saved data is human-readable and 
 // won't break if I later add/change fields in Book or Borrower
    private static final String BOOKS_FILE = "books.txt";
    private static final String BORROWERS_FILE = "borrowers.txt";

    public void saveBooks(List<Book> books){
        
        // the try with resourcees automatically closes  the file when done
        // even if there is an error  partway through writting 
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
       
            // format  so that tehres one book per line 
       
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.author() + "," +
                                book.isbn() + "," + book.getYear() + "," +
                                book.getGenre() + "," + book.isAvailable());
            }
        } catch (IOException e) {
            
            // handling of file related errors only
            System.out.println("There was an Error saving books: " + e.getMessage());
        }
    }
   public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);

        // If no save file exists yet (first run of the program), return an
        // empty list instead of crashing this is a normal case, not an error.
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

        
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                // Defensive check a valid line must have exactly 6 fields.
                // Without this, a corrupted or hand  edited filecould throw
                // ArrayIndexOutOfBoundsException and crash the entire load.
                if (parts.length != 6) {
                System.out.println("Skipping malformed line: " + line);
                continue;
                }

                String title = parts[0];
                String author = parts[1];
                String isbn = parts[2];
                int year = Integer.parseInt(parts[3]);
                String genre = parts[4];
                boolean isAvailable = Boolean.parseBoolean(parts[5]);

               // this sets the availability from saved data, not default
                Book book = new Book(title, author, isbn, year, genre);
                book.setAvailable(isAvailable);
                books.add(book);
            }

        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        return books;
    } 
   public void saveBorrowers(List<Borrower> borrowers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BORROWERS_FILE))) {

            // Format: name,borrowerId,isbn1|isbn2|isbn3,date1|date2|date3
            // "," separates fields, "|" separates multiple items within one field
            for (Borrower borrower : borrowers) {
                String isbns = String.join("|", borrower.getBorrowedISBNs());
                String dates = String.join("|", borrower.getBorrowDates());

                writer.println(borrower.getName() + "," + borrower.getBorrowerId() + "," +
                                isbns + "," + dates);
            }

        } catch (IOException e) {
            System.out.println("There was an Error saving borrowers: " + e.getMessage());
        }
    }
   public List<Borrower> loadBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(BORROWERS_FILE);

        if (!file.exists()) {
            return borrowers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // -1 limit keeps trailing empty fields 
                // borrowed books would otherwise lose their empty isbn/date fields)
                String[] parts = line.split(",", -1);

                if (parts.length != 4) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String name = parts[0];
                String borrowerId = parts[1];
                String isbnField = parts[2];
                String dateField = parts[3];

                Borrower borrower = new Borrower(name, borrowerId);

                // Splitting an empty string still gives a one-element array
                // containing "", which would add a bogus borrowed book — so
                // only split if the field actually has content
                if (!isbnField.isEmpty()) {
                    String[] isbns = isbnField.split("\\|");
                    String[] dates = dateField.split("\\|");

                    for (int i = 0; i < isbns.length; i++) {
                        borrower.addBorrowedBook(isbns[i], dates[i]);
                    }
                }

                borrowers.add(borrower);
            }

        } catch (IOException e) {
            System.out.println("Error loading borrowers: " + e.getMessage());
        }

        return borrowers;
    }
} 

