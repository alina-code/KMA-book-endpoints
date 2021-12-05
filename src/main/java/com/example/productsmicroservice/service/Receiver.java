package com.example.productsmicroservice.service;

import com.example.productsmicroservice.config.MessagingConfiguration;
import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.model.BookRequest;
import com.example.productsmicroservice.repository.JpaBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Receiver {

    private final JpaBookRepository repository;

    @RabbitListener(queues = MessagingConfiguration.queueName)
    public void receiveMessage(BookRequest bookRequest) {
            System.out.println("Received <" + bookRequest + ">");

            Book book = new Book();
            book.setName(bookRequest.getName());
            book.setAuthor(bookRequest.getAuthor());
            book.setPrice(bookRequest.getPrice());

            repository.save(book);
        }





}
