package de.uni_bayreuth.se.testingdemo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetSeededBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void shouldCreateAndFetchBook() throws Exception {
        String json = """
                {
                  \"title\": \"Patterns of Enterprise Application Architecture\",
                  \"author\": \"Martin Fowler\",
                  \"year\": 2002
                }
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Patterns of Enterprise Application Architecture"));

        mockMvc.perform(get("/api/books/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Martin Fowler"));
    }
}
