package com.example.productsmicroservice.controller;

import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Controller.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;

    private final UUID productId = UUID.randomUUID();



    @Test
    void findProduct() throws Exception {
        when(service.findById(productId)).thenReturn((new Book()));
        this.mockMvc.perform(get( "/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findProduct_Failure() throws Exception {
        when(service.findById(productId)).thenThrow(new EntityNotFoundException("not found"));
        this.mockMvc.perform(get("/" + productId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void findAllWithoutParams_Success() throws Exception {
        this.mockMvc.perform(get(""))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void findAllWithParams_Success() throws Exception {
        this.mockMvc.perform(get("/")
        .param("page", "0")
        .param("size", "10")
        .param("productName", "Apple"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    void findAllWithWrongParams_Failure() throws Exception {
        this.mockMvc.perform(get("/"  )
                .param("page", "alalal0")
                .param("size", "10")
                .param("productName", "Apple"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("'page' should be a valid 'int' and 'alalal0' isn't")));
    }




}