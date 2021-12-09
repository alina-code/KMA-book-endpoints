package com.example.productsmicroservice.service;

import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.model.BookRequest;
import com.example.productsmicroservice.repository.JpaBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private JpaBookRepository repository;
    @Mock
    private RabbitTemplate template;

    @InjectMocks
    private ProductServiceImpl productService;

    private final UUID productId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        lenient().when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void findById_Success() {
        Book book = getProduct();
        when(repository.findById(productId)).thenReturn(java.util.Optional.of(book));
        productService.findById(productId);

        verify(repository).findById(productId);
    }

    @Test
    void findByIdWhenNotExist_Failure() {
        when(repository.findById(productId)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.findById(productId);
        });

        String expectedMessage = "Product " + productId + " does not exist!";

        verify(repository).findById(productId);
        assertThat(exception.getMessage()).contains(expectedMessage);
    }


@Test
void addProduct_Success() {
           BookRequest book = new BookRequest();
           book.setName("Book");
    when(repository.existsByName("Book")).thenReturn(false);

    String response = productService.add(book);

    verify(template).convertAndSend(any(), any(),eq(book));
    verify(repository).existsByName(any());
    assertThat(response).isEqualTo("Book was added to queue");
}


    private Book getProduct() {
        Book book = new Book();
        book.setId(productId);
        book.setName("Apple");
        book.setPrice(new BigDecimal("1.1"));
        return book;
    }

}