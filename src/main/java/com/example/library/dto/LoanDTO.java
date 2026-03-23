package com.example.library.dto;

import com.example.library.model.Loan;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;

public class LoanDTO {
    @Id
    private Integer loanId;

    private Integer readerId;

    private String readerName;

    private String readerLastName;

    private String readerPhone;

    private LocalDate loanCreatedDate;

    private LocalDate loanReturnDate;

    private Integer loanStatus;

    private Integer bookId;

    private String bookName;

    private String bookISBN;

    private List<String> bookAuthors;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
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

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderLastName() {
        return readerLastName;
    }

    public void setReaderLastName(String readerLastName) {
        this.readerLastName = readerLastName;
    }

    public String getReaderPhone() {
        return readerPhone;
    }

    public void setReaderPhone(String readerPhone) {
        this.readerPhone = readerPhone;
    }

    public Integer getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Integer loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public List<String> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<String> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public LoanDTO() {
    }

    public LoanDTO(Integer loanId, Integer readerId, LocalDate loanCreatedDate, LocalDate loanReturnDate, Integer loanStatus, Integer bookId) {
        this.loanId = loanId;
        this.readerId = readerId;
        this.loanCreatedDate = loanCreatedDate;
        this.loanReturnDate = loanReturnDate;
        this.loanStatus = loanStatus;
        this.bookId = bookId;
    }

    public LoanDTO(Loan entity) {
        this.loanId = entity.getLoanId();
        this.loanStatus = entity.getLoanStatus();
        this.loanCreatedDate = entity.getLoanCreatedDate();
        this.loanReturnDate = entity.getLoanReturnDate();

        this.bookId = entity.getBook().getBookId();
        this.bookName = entity.getBook().getBookName();
        this.bookISBN = entity.getBook().getBookISBN();
        this.bookAuthors = entity.getBook().getAuthors()
                .stream().map(a -> a.getAuthorName() + " " + a.getAuthorLastname())
                .toList();

        this.readerId = entity.getReader().getReaderId();
        this.readerName = entity.getReader().getReaderName();
        this.readerLastName = entity.getReader().getReaderLastname();
        this.readerPhone = entity.getReader().getReaderPhone();
    }
}
