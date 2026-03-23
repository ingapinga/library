package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.dto.LoanDTO;
import com.example.library.service.BookService;
import com.example.library.service.LoanService;
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
@RequestMapping("/reader")
@Transactional
public class ReaderController {
    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);

    @Autowired
    private final BookService bookService;
    @Autowired
    private final LoanService loanService;

    public ReaderController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookDTO>> getAll(Pageable pageable) {
        return new ResponseEntity<>(bookService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/books/available")
    public ResponseEntity<List<BookDTO>> getAvailable() {
        return new ResponseEntity<>(bookService.getAvailable(), HttpStatus.OK);
    }

    @PostMapping("/loans/reserve")
    public ResponseEntity<LoanDTO> reserve(@RequestBody LoanBookRequest bookRequest) {
        return new ResponseEntity<>(loanService.reserve(bookRequest.bookId, bookRequest.readerId), HttpStatus.OK);
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

}
