package com.example.productsmicroservice.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class BookPage {
    List<Book> books;

    int pageNumber;
    int pageSize;
    long totalElements;


    public BookPage(Page<Book> page) {
        this.books = page.getContent();
        this.pageNumber = page.getPageable().getPageNumber();
        this.pageSize = page.getPageable().getPageSize();
        this.totalElements= page.getTotalElements();
    }
}