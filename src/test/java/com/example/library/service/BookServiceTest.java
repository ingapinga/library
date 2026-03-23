package com.example.library.service;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void saveBookNewAuthorTest() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorName("Тест");
        authorDTO.setAuthorLastname("Тестов");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookISBN("123-321");
        bookDTO.setBookName("Тест");
        bookDTO.setBookQuantity(1);
        bookDTO.setBookAvailable(1);
        bookDTO.setAuthors(Set.of(authorDTO));

        Author savedAuthor = new Author(1, "Тест", "Тестов");
        when(authorRepository.save(any())).thenReturn(savedAuthor);

        Book savedBook = new Book();
        savedBook.setBookId(1);
        savedBook.setAuthors(Set.of(savedAuthor));
        when(bookRepository.save(any())).thenReturn(savedBook);

        BookDTO result = bookService.save(bookDTO);

        assertNotNull(result);
        verify(bookRepository).save(any());
        verify(authorRepository).save(any());
    }

    @Test
    void saveBookExistingAuthorTest() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorId(1);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookISBN("123-321");
        bookDTO.setBookName("Тест");
        bookDTO.setBookQuantity(1);
        bookDTO.setBookAvailable(1);
        bookDTO.setAuthors(Set.of(authorDTO));

        Author author = new Author(1, "Тест", "Тестов");
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        Book savedBook = new Book();
        savedBook.setBookId(1);
        savedBook.setAuthors(Set.of(author));
        when(bookRepository.save(any())).thenReturn(savedBook);

        BookDTO result = bookService.save(bookDTO);

        assertNotNull(result);
        verify(bookRepository).save(any());
        verify(authorRepository, never()).save(any());
    }
}
