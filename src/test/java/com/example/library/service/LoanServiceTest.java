package com.example.library.service;


import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    LoanRepository loanRepository;

    @Test
    @Transactional
    void loanBookTest() {
        // проверка добавления loan и проверка уменьшения количества книг
        Book book = bookRepository.findById(2).get();
        Integer available = book.getBookAvailable();

        long loanCount = loanRepository.count();

        loanService.loanBook(book.getBookId(), 2);

        long newLoanCount = loanRepository.count();

        assertEquals(loanCount+1, newLoanCount);
        assertEquals(available-1, bookRepository.findById(book.getBookId()).get().getBookAvailable());
    }

    @Test
    @Transactional
    void reserveBookTest() {
        // проверка добавления loan и проверка уменьшения количества книг
        Book book = bookRepository.findById(3).get();
        Integer available = book.getBookAvailable();

        long loanCount = loanRepository.count();

        loanService.loanBook(book.getBookId(), 1);

        long newLoanCount = loanRepository.count();

        assertEquals(loanCount+1, newLoanCount);
        assertEquals(available-1, bookRepository.findById(book.getBookId()).get().getBookAvailable());
    }

    @Test
    @Transactional
    void reserveNotAvailableBookTest() {
        // проверка при недоступности книги для резервации
        assertThrows(RuntimeException.class, () -> loanService.loanBook(1, 1));
    }
}
