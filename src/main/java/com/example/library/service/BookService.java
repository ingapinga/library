package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BookDTO save(BookDTO bookDTO) {
        if (bookRepository.existsByBookISBN(bookDTO.getBookISBN())) {
            throw new RuntimeException("ISBN уже существует");
        }
        if (bookDTO.getAuthors() == null || bookDTO.getAuthors().isEmpty()) {
            throw new RuntimeException("Необходимо указать авторов");
        }

        Book book = bookDTO.toEntity();

        // Добавление авторов. При id==null добавляется новый, иначе сохраняется существующий по id (исключение если нет в БД)
        Set<Author> authors = bookDTO.getAuthors().stream()
                .map(authorDTO -> {
                    return authorDTO.getAuthorId()==null
                            ? authorRepository.save(
                                    new Author(null, authorDTO.getAuthorName(), authorDTO.getAuthorLastname()))
                            : authorRepository.findById(authorDTO.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Автор не найден"));
                }).collect(Collectors.toSet());
        book.setAuthors(authors);

        Book newBook = bookRepository.save(book);
        return new BookDTO(newBook);
    }

    public Page<BookDTO> getAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page.map(BookDTO::new);
    }

    public List<BookDTO> getAvailable() {
        return bookRepository.findByBookAvailableGreaterThan(0).stream().map(BookDTO::new).toList();
    }
}
