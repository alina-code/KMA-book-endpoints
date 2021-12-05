package com.example.productsmicroservice.repository;

import com.example.productsmicroservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<Book, UUID> {
    boolean existsByName(String name);


}
