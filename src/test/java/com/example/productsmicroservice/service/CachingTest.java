package com.example.productsmicroservice.service;


import com.example.productsmicroservice.config.CacheConfig;
import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.repository.JpaBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Import({ CacheConfig.class, ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
 class CachingTest {


        @MockBean
        private JpaBookRepository repository;

        @MockBean
        private RabbitTemplate template;

        private ProductService service;

        @Autowired
        private CacheManager cacheManager;

        @BeforeEach
        void setUp(){
           service= new ProductServiceImpl(template, repository);
        }

        @Test
        void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
            UUID id = UUID.randomUUID();
            Book book = new Book();
            book.setPrice(BigDecimal.ONE);
            book.setName("name");
            book.setAuthor("author");
            book.setId(id);

            when(repository.findById(id)).thenReturn(Optional.of(book));

            Book bookCacheMiss = service.findById(id);
            Book bookCacheHit = service.findById(id);

            assertThat(bookCacheMiss).isEqualTo(book);
            assertThat(bookCacheHit).isEqualTo(book);

            verify(repository, times(1)).findById(id);
         //   assertThat(itemFromCache()).isEqualTo(book);
        }
    }

