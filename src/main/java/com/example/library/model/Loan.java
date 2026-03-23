package com.example.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Loan {

    public static final int STATUS_RESERVED = 1;
    public static final int STATUS_BORROWED = 2;
    public static final int STATUS_RETURNED = 3;

    @Id
    @Column(name = "loan_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_gen")
    @SequenceGenerator(name = "loan_id_gen", sequenceName = "loan_id_gen",  allocationSize=1)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Column(name="loan_createddate", nullable = false)
    private LocalDate loanCreatedDate;

    @Column(name="loan_returndate")
    private LocalDate loanReturnDate;

    @Column(name="loan_status", nullable = false)
    private Integer loanStatus;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDate getLoanCreatedDate() {
        return loanCreatedDate;
    }

    public void setLoanCreatedDate(LocalDate loanCreatedDate) {
        this.loanCreatedDate = loanCreatedDate;
    }

    public LocalDate getLoanReturnDate() {
        return loanReturnDate;
    }

    public void setLoanReturnDate(LocalDate loanReturnDate) {
        this.loanReturnDate = loanReturnDate;
    }

    public Integer getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Integer loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Loan(Integer loanId, Book book, Reader reader, LocalDate loanCreatedDate, LocalDate loanReturnDate, Integer loanStatus) {
        this.loanId = loanId;
        this.book = book;
        this.reader = reader;
        this.loanCreatedDate = loanCreatedDate;
        this.loanReturnDate = loanReturnDate;
        this.loanStatus = loanStatus;
    }

    public Loan() {
    }
}
