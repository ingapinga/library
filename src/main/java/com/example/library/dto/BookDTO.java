package com.example.library.dto;

import com.example.library.model.Book;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {

    @Id
    private Integer bookId;

    private String bookName;

    private String bookISBN;

    private Integer bookQuantity;

    private Integer bookAvailable;

    private Set<AuthorDTO> authors;

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

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public BookDTO() {
    }

    public BookDTO(Integer bookId, String bookName,
                   String bookISBN, Integer bookQuantity,
                   Integer bookAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookISBN = bookISBN;
        this.bookQuantity = bookQuantity;
        this.bookAvailable = bookAvailable;
    }

    public BookDTO(Book entity) {
        this.bookId = entity.getBookId();
        this.bookName = entity.getBookName();
        this.bookISBN = entity.getBookISBN();
        this.bookAvailable = entity.getBookAvailable();
        this.bookQuantity = entity.getBookQuantity();

        if (entity.getAuthors() != null) {
            this.authors = entity.getAuthors().stream()
                    .map(author -> {
                        return new AuthorDTO(author.getAuthorId(), author.getAuthorName(), author.getAuthorLastname());
                    }).collect(Collectors.toSet());
        }

    }

    public Book toEntity() {
        return toEntity(new Book());
    }

    public Book toEntity(Book entity) {
        entity.setBookId(this.bookId);
        entity.setBookName(this.bookName);
        entity.setBookISBN(this.bookISBN);
        entity.setBookAvailable(this.bookAvailable);
        entity.setBookQuantity(this.bookQuantity);

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(bookId, bookDTO.bookId) && Objects.equals(bookName, bookDTO.bookName) && Objects.equals(bookISBN, bookDTO.bookISBN) && Objects.equals(bookQuantity, bookDTO.bookQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, bookISBN);
    }
}
