/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Administrator
 */
public class Book {

    private String title;
    private String author;
    private String isbn;
    private int year;
    private String genre;
    private boolean isAvailable;
    
// the above code refers to the attributes which decribe each and every single book  and i used private so that no other class should directly  touch these values  but basically for encapsulation 
// Private attributes for encapsulation to describe each book
    public Book(String title,String author,String isbn,int year,String genre){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
        this.isAvailable = true;
    }
    public String getTitle() {
        return title;
    }
    public String author() {
        return author;
    }
    public String isbn(){
        return isbn;
    }
    public int getYear(){
        return year;
    } 
    public String getGenre(){
        return genre;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable( boolean available){
        this.isAvailable = available ;
    }
// Getters and setters provide controlled access to private fields (encapsulation)
// Only isAvailable has a setter because book details like title and author never change            
     
}
