package com.example.library.repository;

import com.example.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository  extends JpaRepository<Loan, Integer> {
    List<Loan> findByLoanReturnDateBeforeAndLoanStatus(
            LocalDate date,
            Integer status
    );

    List<Loan> findByReaderReaderIdAndBookBookIdAndLoanStatus(
            Integer readerId,
            Integer bookId,
            Integer status
    );

    @Query("""
        SELECT DISTINCT l FROM Loan l
        JOIN l.book b
        JOIN b.authors a
        WHERE 
            LOWER(b.bookName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(b.bookISBN) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CONCAT(a.authorName, ' ', a.authorLastname)) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<Loan> searchLoans(@Param("query") String query);
}
