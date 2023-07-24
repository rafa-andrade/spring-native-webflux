package com.example.snw.service;

import com.example.snw.domain.Product;
import com.example.snw.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

@SpringBootTest
public class ProductServiceTest {

  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Test
  public void shouldSaveProduct() {
    Product product = getProduct();
    doReturn(just(product)).when(productRepository).save(product);

    productService.save(product).subscribe();

    verify(productRepository, times(1)).save(product);
  }

  @Test
  public void shouldUpdateProduct() {
    Product product = getProduct();
    String productId = UUID.randomUUID().toString();

    doReturn(just(product)).when(productRepository).findById(productId);
    doReturn(just(product)).when(productRepository).save(product);

    productService.update(productId, product).subscribe();

    verify(productRepository, times(1)).save(product);
  }

  @Test
  public void shouldNotUpdateProduct() {
    Product product = getProduct();
    String productId = UUID.randomUUID().toString();

    doReturn(empty()).when(productRepository).findById(productId);

    create(productService.update(productId, product)).expectError(NoSuchElementException.class);

    verify(productRepository, times(0)).save(product);
  }

  @Test
  public void shouldDeleteProduct() {
    Product product = getProduct();
    String productId = UUID.randomUUID().toString();

    doReturn(just(product)).when(productRepository).findById(productId);
    doReturn(empty()).when(productRepository).deleteById(productId);

    productService.deleteById(productId).subscribe();

    verify(productRepository, times(1)).deleteById(productId);
  }

  @Test
  public void shouldNotDeleteProduct() {
    String productId = UUID.randomUUID().toString();

    doReturn(empty()).when(productRepository).findById(productId);

    create(productService.deleteById(productId)).expectError(NoSuchElementException.class);

    verify(productRepository, times(0)).deleteById(productId);
  }

  private Product getProduct() {
    Product product = new Product();
    product.setCategory("Category Test");
    product.setStock(BigInteger.TEN);
    product.setPrice(BigDecimal.TEN);
    product.setName("Product Test");
    return product;
  }
}
