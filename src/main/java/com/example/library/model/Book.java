package com.example.library.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name="book_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_gen")
    @SequenceGenerator(name = "book_id_gen", sequenceName = "book_id_gen",  allocationSize=1)
    private Integer bookId;

    @Column(name= "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_isbn", nullable = false, unique = true)
    private String bookISBN;

    @Column(name = "book_quantity", nullable = false)
    private Integer bookQuantity;

    @Column(name = "book_available", nullable = false)
    private Integer bookAvailable;

    @ManyToMany
    @JoinTable(name = "authorbook",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    Set<Author> authors;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public Integer getBookAvailable() {
        return bookAvailable;
    }

    public void setBookAvailable(Integer bookAvailable) {
        this.bookAvailable = bookAvailable;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Book() {
    }

    public Book(Integer bookId, String bookName,
                String bookISBN, Integer bookQuantity,
                Integer bookAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookISBN = bookISBN;
        this.bookQuantity = bookQuantity;
        this.bookAvailable = bookAvailable;
    }

}
