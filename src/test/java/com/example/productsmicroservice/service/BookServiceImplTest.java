package com.example.productsmicroservice.service;

import com.example.productsmicroservice.model.Book;
import com.example.productsmicroservice.repository.JpaBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void findAll_Success() {
//
//        ProductResponse productResponse = new ProductResponse(getProduct());
//        PageImpl<ProductResponse> page = new PageImpl<>(Collections.singletonList(productResponse), Pageable.ofSize(10), 1);
//
//        when(repository.findAll(PageRequest.of(0, 10), "%apple%")).thenReturn(page);
//
//        ProductPage productPage = productService.findAll(PageRequest.of(0, 10), "apple");
//
//        assertThat(productPage).isNotNull();
//        verify(repository).findAll(PageRequest.of(0, 10), "%apple%");
    }




@Test
void addProduct_Success() throws InterruptedException {
//    ProductRequest productRequest = new ProductRequest();
//    productRequest.setProductName("banana");
//
//    when(repository.existsByProductName(productRequest.getProductName())).thenReturn(false);
//
//    ProductResponse product = productService.add(productRequest);
//
//    verify(repository).save(any());
//    assertThat(product.getProductName()).isEqualTo(productRequest.getProductName());
}
    @Test
    void addWhenProductWithThisNameExists_Failure() {
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setProductName("banana");
//
//        when(repository.existsByProductName(productRequest.getProductName())).thenReturn(true);
//
//       Exception exception = assertThrows(EntityExistsException.class,()->
//               productService.add(productRequest));
//
//        verify(repository, never()).save(any());
//        assertThat(exception.getMessage()).contains("Product with this name already exists");
    }


    private Book getProduct() {
        Book book = new Book();
        book.setId(productId);
        book.setName("Apple");
        book.setPrice(new BigDecimal("1.1"));
        return book;
    }

}