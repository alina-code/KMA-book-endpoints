package com.example.productsmicroservice.controller;


import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.model.BookPage;
import com.example.productsmicroservice.model.BookRequest;
import com.example.productsmicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
@Validated
public class Controller {

    private final ProductService productService;



    @GetMapping("{id}")
    public ResponseEntity<Book> findProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<BookPage> findAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10")  int size) {
        return ResponseEntity.ok(productService.findAll(PageRequest.of(page,size)));
    }


    @PostMapping()
    public ResponseEntity<String> addProduct(@RequestBody @Valid BookRequest bookRequest
    )  {
        return ResponseEntity.ok(productService.add(bookRequest));
    }




}
