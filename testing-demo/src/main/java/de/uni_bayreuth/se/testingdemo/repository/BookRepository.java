package de.uni_bayreuth.se.testingdemo.repository;

import de.uni_bayreuth.se.testingdemo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Clean Code", "Robert C. Martin", 2008),
            new Book(2L, "Refactoring", "Martin Fowler", 1999)
    ));

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> book.id().equals(id)).findFirst();
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }
}
