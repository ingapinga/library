package com.example.library.service;

import com.example.library.dto.LoanDTO;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Reader;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private final LoanRepository loanRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final ReaderRepository readerRepository;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       ReaderRepository readerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @Transactional
    public LoanDTO loanBook(Integer bookId, Integer readerId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("Книга не найдена"));
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new EntityNotFoundException("Читатель не найден"));

        Loan loan;
        List<Loan> reservations = loanRepository.findByReaderReaderIdAndBookBookIdAndLoanStatus(readerId, bookId, Loan.STATUS_RESERVED);
        if (!reservations.isEmpty()) {
            // при наличии у читателя резервации данной книги используем ее
            loan = reservations.get(0);
        } else {
            // иначе создае новую
            if(book.getBookAvailable() < 1) {
                throw new RuntimeException("Данной книги нет в наличии");
            }
            loan = new Loan();
            loan.setBook(book);
            loan.setReader(reader);
            loan.setLoanCreatedDate(LocalDate.now());
            book.setBookAvailable(book.getBookAvailable() - 1);
        }

        loan.setLoanStatus(Loan.STATUS_BORROWED);
        loan.setLoanReturnDate(LocalDate.now().plusDays(14));
        loanRepository.save(loan);
        bookRepository.save(book);

        return new LoanDTO(loan);
    }

    public List<LoanDTO> getOverdue() {
        List<Loan> loans = loanRepository.findByLoanReturnDateBeforeAndLoanStatus(
                LocalDate.now(),
                Loan.STATUS_BORROWED
        );
        return loans.stream().map(LoanDTO::new).toList();
    }

    @Transactional
    public LoanDTO reserve(Integer bookId, Integer readerId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("Книга не найдена"));
        if(book.getBookAvailable() < 1) {
            throw new RuntimeException("Данной книги нет в наличии");
        }
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new EntityNotFoundException("Читатель не найден"));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setLoanCreatedDate(LocalDate.now());
        loan.setLoanStatus(Loan.STATUS_RESERVED);
        book.setBookAvailable(book.getBookAvailable() - 1);

        loanRepository.save(loan);
        bookRepository.save(book);
        return new LoanDTO(loan);
    }


    public List<LoanDTO> searchLoans(String q) {
        List<Loan> loans = loanRepository.searchLoans(q);

        return loans.stream().map(LoanDTO::new).toList();
    }
}
