package de.uni_bayreuth.se.testingdemo.service;

import de.uni_bayreuth.se.testingdemo.dto.BookRequest;
import de.uni_bayreuth.se.testingdemo.exception.BookNotFoundException;
import de.uni_bayreuth.se.testingdemo.model.Book;
import de.uni_bayreuth.se.testingdemo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + id));
    }

    public Book createBook(BookRequest request) {
        long nextId = repository.findAll().stream()
                .mapToLong(Book::id)
                .max()
                .orElse(0L) + 1;

        Book book = new Book(nextId, request.title(), request.author(), request.year());
        return repository.save(book);
    }
}
