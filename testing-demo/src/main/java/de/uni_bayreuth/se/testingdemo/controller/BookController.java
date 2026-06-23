package de.uni_bayreuth.se.testingdemo.controller;

import de.uni_bayreuth.se.testingdemo.dto.BookRequest;
import de.uni_bayreuth.se.testingdemo.model.Book;
import de.uni_bayreuth.se.testingdemo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @PostMapping
    public Book createBook(@Valid @RequestBody BookRequest request) {
        return service.createBook(request);
    }
}
