package com.example.library.repository;

import com.example.library.dto.BookDTO;
import com.example.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @EntityGraph(attributePaths = {"authors"})
    Page<Book> findAll(Pageable pageable);

    boolean existsByBookISBN(String isbn);

    List<Book> findByBookAvailableGreaterThan(int i);
}
