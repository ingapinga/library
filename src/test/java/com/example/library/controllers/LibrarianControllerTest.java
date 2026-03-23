package com.example.library.controllers;

import com.example.library.controller.LibrarianController;
import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import com.example.library.dto.LoanDTO;
import com.example.library.model.Author;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class LibrarianControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    @Rollback
    public void saveBookTest() throws Exception {
        // проверка сохранения с ошибками
        this.mockMvc.perform(post("/librarian/books")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        BookDTO dto = new BookDTO();
        String checkValue = "Отцы и дети";
        String fieldName = "bookName";
        dto.setBookISBN("978-5-392-41450-5");
        dto.setBookName(checkValue);
        dto.setBookAvailable(2);
        dto.setBookQuantity(2);
        Set<AuthorDTO> authors = new HashSet<>();
        authors.add(new AuthorDTO(1, "Федор",	"Достоевский"));
        authors.add(new AuthorDTO(null, "Фёдор",	"Достоевский"));
        dto.setAuthors(authors);

        this.mockMvc.perform(post("/librarian/books")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fieldName,checkValue))));
    }

    @Test
    @Transactional
    @Rollback
    public void saveLoanTest() throws Exception {
        // проверка сохранения с ошибками
        this.mockMvc.perform(post("/librarian/loans")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        LibrarianController.LoanBookRequest dto = new LibrarianController.LoanBookRequest();
        dto.setBookId(1);
        dto.setReaderId(1);

        this.mockMvc.perform(post("/librarian/loans")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("\"errorMessage\":\"Данной книги нет в наличии\"")));

        dto.setBookId(2);
        dto.setReaderId(1);

        this.mockMvc.perform(post("/librarian/loans")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andExpect(content().string(containsString("\"loanStatus\":2")));
    }

    @Test
    public void getOverdueTest() throws Exception {
        LocalDate date = LocalDate.now();

        MvcResult result = this.mockMvc.perform(get("/librarian/loans/getoverdue"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"readerLastName\":\"Иванов\"")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<LoanDTO> dto;
        dto = new ObjectMapper().readValue(content, new TypeReference<List<LoanDTO>>() {});
        assertTrue(date.isAfter(dto.get(0).getLoanReturnDate()));
    }

    @Test
    public void searchLoansTest() throws Exception {
        this.mockMvc.perform(get("/librarian/loans/search")
                .param("q", "Федор Достоевский"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"bookName\":\"Преступление и наказание\"")));

        this.mockMvc.perform(get("/librarian/loans/search")
                .param("q", "978-5-17-"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("978-5-17-090630-7")))
                .andExpect(content().string(containsString("978-5-17-115274-1")))
                .andExpect(content().string(not(containsString("978-5-17-139129-4"))))  // не находится на руках
                .andExpect(content().string(not(containsString("978-985-579-596-5")))); // не совпадает с поиском

    }



}
