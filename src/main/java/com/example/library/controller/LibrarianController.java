package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.dto.LoanDTO;
import com.example.library.service.BookService;
import com.example.library.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarian")
@Transactional
public class LibrarianController {
    private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);

    @Autowired
    private final BookService bookService;

    @Autowired
    private final LoanService loanService;

    public LibrarianController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.save(bookDTO), HttpStatus.OK);
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanDTO> saveLoan(@RequestBody LoanBookRequest bookRequest) {
        return new ResponseEntity<>(loanService.loanBook(bookRequest.bookId, bookRequest.readerId), HttpStatus.OK);
    }

    public static class LoanBookRequest {
        private Integer bookId;
        private Integer readerId;

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public Integer getReaderId() {
            return readerId;
        }

        public void setReaderId(Integer readerId) {
            this.readerId = readerId;
        }

        public LoanBookRequest() {
        }

        public LoanBookRequest(Integer bookId, Integer readerId) {
            this.bookId = bookId;
            this.readerId = readerId;
        }
    }

    @GetMapping("/loans/getoverdue")
    public ResponseEntity<List<LoanDTO>> getOverdue() {
        return new ResponseEntity<>(loanService.getOverdue(), HttpStatus.OK);
    }

    @GetMapping("/loans/search")
    public List<LoanDTO> searchLoans(@RequestParam String q) {
        return loanService.searchLoans(q);
    }
}
