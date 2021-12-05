package com.example.productsmicroservice.service;

import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.model.BookPage;
import com.example.productsmicroservice.model.BookRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Book findById(UUID id);
    
    String add(BookRequest bookRequest) ;

    BookPage findAll(Pageable p);


}
