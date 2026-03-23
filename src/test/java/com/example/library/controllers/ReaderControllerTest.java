package com.example.library.controllers;

import com.example.library.controller.ReaderController;
import com.example.library.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ReaderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllBooksTest() throws Exception {
        this.mockMvc.perform(get("/reader/books")
                        .param("page", "0")
                        .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalPages\":2")))
                .andExpect(content().string(containsString("\"totalElements\":4")));

        this.mockMvc.perform(get("/reader/books")
                        .param("page", "10")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalPages\":1")))
                .andExpect(content().string(containsString("\"empty\":true")));

    }

    @Test
    public void getAvailableBooksTest() throws Exception {
        this.mockMvc.perform(get("/reader/books/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"bookAvailable\":0"))))
                .andExpect(content().string(not(containsString("Преступление и наказание"))));
    }

    @Test
    @Transactional
    public void reserveTest() throws Exception {
        // проверка сохранения с ошибками
        this.mockMvc.perform(post("/reader/loans/reserve")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ReaderController.LoanBookRequest dto = new ReaderController.LoanBookRequest();
        dto.setBookId(1);
        dto.setReaderId(1);

        this.mockMvc.perform(post("/reader/loans/reserve")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("\"errorMessage\":\"Данной книги нет в наличии\"")));

        dto.setBookId(2);
        dto.setReaderId(1);

        this.mockMvc.perform(post("/reader/loans/reserve")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andExpect(content().string(containsString("\"loanStatus\":1")));

    }
}
