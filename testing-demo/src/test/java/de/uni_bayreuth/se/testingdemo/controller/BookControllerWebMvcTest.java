package de.uni_bayreuth.se.testingdemo.controller;

import de.uni_bayreuth.se.testingdemo.exception.BookNotFoundException;
import de.uni_bayreuth.se.testingdemo.model.Book;
import de.uni_bayreuth.se.testingdemo.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Test
    void shouldReturnAllBooks() throws Exception {
        when(service.getAllBooks()).thenReturn(List.of(
                new Book(1L, "Clean Code", "Robert C. Martin", 2008)
        ));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void shouldReturn404WhenBookMissing() throws Exception {
        when(service.getBookById(99L)).thenThrow(new BookNotFoundException("Book not found: 99"));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book not found: 99"));
    }

    @Test
    void shouldReturn400ForInvalidBookRequest() throws Exception {
        String invalidJson = """
                {
                  \"title\": \"\",
                  \"author\": \"Martin Fowler\",
                  \"year\": 1800
                }
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.year").exists());
    }
}
