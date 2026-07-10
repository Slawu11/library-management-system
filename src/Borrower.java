/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Administrator
 */
import java.util.ArrayList;
public class Borrower {
    private String name;
    private String borrowerId;
    private ArrayList<String> borrowedBookISBNs;
    private ArrayList<String> borrowDates;
    
    //construct to create a borrower
    public Borrower (String name, String borrowerId ){
        this.name = name;
        this.borrowerId = borrowerId;
        this.borrowedBookISBNs = new ArrayList <> ();
        this.borrowDates =  new ArrayList <> ();
        
        // Borrower has name + ID. ArrayList tracks borrowed books 
        // it  grows and shrinks as needed
    }
    
    public String getName(){
        return name;
    }
    public String getBorrowerId(){
        return borrowerId;
    }
    public ArrayList<String> getBorrowedISBNs(){
        return new ArrayList<>(borrowedBookISBNs); // return a copy
    }
    public ArrayList<String>  getBorrowDates(){
    return new ArrayList<>(borrowDates); // return a copy
    }
    public void addBorrowedBook(String isbn , String borrowDate){
        borrowedBookISBNs.add(isbn);
        borrowDates.add(borrowDate);
              // Getters provide access to borrower data
     // addBorrowedBook and returnBook manage the list of borrowed books
    }

   public void returnBook(String isbn) {
    int index = borrowedBookISBNs.indexOf(isbn);
    if (index != -1) {
        borrowedBookISBNs.remove(index);
        borrowDates.remove(index);
    }
}
}
    
