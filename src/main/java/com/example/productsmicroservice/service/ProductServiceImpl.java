package com.example.productsmicroservice.service;


import com.example.productsmicroservice.config.MessagingConfiguration;
import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.model.BookPage;
import com.example.productsmicroservice.model.BookRequest;
import com.example.productsmicroservice.repository.JpaBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RabbitTemplate rabbitTemplate;


    private final JpaBookRepository productRepository;

    @Override
    @Cacheable(value = "bookCache")
    public Book findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(productDoesNotExist(id)));
    }



    @Override
    public String add(BookRequest bookRequest) {
       // addToQueue
      if (productRepository.existsByName(bookRequest.getName()))
            throw new EntityExistsException(productAlreadyExists(bookRequest.getName()));

        rabbitTemplate.convertAndSend(MessagingConfiguration.topicExchangeName, "foo.bar.baz", bookRequest);

        return "Book was added to queue";

    }

    @Override
    @Cacheable(value = "bookCache")
    public BookPage findAll(Pageable p) {
        return new BookPage(productRepository.findAll(p));
    }


    private static String productDoesNotExist(UUID id) {
        return "Product " + id + " does not exist!";
    }

    private static String productAlreadyExists(String name) {
        return "Product with this name already exists: " + name;
    }
}
