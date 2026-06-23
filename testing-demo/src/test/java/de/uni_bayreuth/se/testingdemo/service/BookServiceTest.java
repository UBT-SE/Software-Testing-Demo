package de.uni_bayreuth.se.testingdemo.service;

import de.uni_bayreuth.se.testingdemo.dto.BookRequest;
import de.uni_bayreuth.se.testingdemo.exception.BookNotFoundException;
import de.uni_bayreuth.se.testingdemo.model.Book;
import de.uni_bayreuth.se.testingdemo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    void shouldReturnBookById() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", 2008);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book result = service.getBookById(1L);

        assertEquals("Clean Code", result.title());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowWhenBookNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> service.getBookById(99L));
        verify(repository).findById(99L);
    }

    @Test
    void shouldCreateBook() {
        when(repository.findAll()).thenReturn(List.of(
                new Book(1L, "Clean Code", "Robert C. Martin", 2008)
        ));
        when(repository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = service.createBook(new BookRequest("DDD", "Eric Evans", 2003));

        assertEquals(2L, result.id());
        assertEquals("DDD", result.title());
        verify(repository).save(any(Book.class));
    }
}
