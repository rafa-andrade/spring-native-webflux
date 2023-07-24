package com.example.snw.controller;

import com.example.snw.controller.dto.ProductDTO;
import com.example.snw.controller.mapper.ProductMapper;
import com.example.snw.domain.Product;
import com.example.snw.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
public class ProductControllerTest {
  @MockBean
  private ProductService productService;

  @SpyBean
  private ProductMapper productMapper;

  @Autowired
  private WebTestClient webClient;

  @Test
  public void shouldCreateProduct() {
    ProductDTO productDTO = getProductDTO();
    doReturn(just(productMapper.toDomain(productDTO))).when(productService).save(any(Product.class));

    webClient.post().uri("/products")
        .contentType(APPLICATION_JSON)
        .body(fromObject(productDTO))
        .exchange()
        .expectStatus()
        .isCreated();

    verify(productService, times(1)).save(any(Product.class));
  }

  @Test
  public void shouldUpdateProduct() {
    ProductDTO productDTO = getProductDTO();
    String productId = UUID.randomUUID().toString();

    doReturn(just(productMapper.toDomain(productDTO))).when(productService).update(anyString(),any(Product.class));

    webClient.put().uri("/products/" + productId)
        .contentType(APPLICATION_JSON)
        .body(fromObject(productDTO))
        .exchange()
        .expectStatus()
        .isOk();

    verify(productService, times(1)).update(anyString(), any(Product.class));
  }

  @Test
  public void shouldDeleteProductById() {
    String productId = UUID.randomUUID().toString();
    doReturn(Mono.empty()).when(productService).deleteById(productId);

    webClient.delete().uri("/products/" + productId)
        .exchange()
        .expectStatus()
        .isOk();

    verify(productService, times(1)).deleteById(productId);
  }

  @Test
  public void shouldFindProductById() {
    ProductDTO productDTO = getProductDTO();
    String productId = UUID.randomUUID().toString();

    Product product = productMapper.toDomain(productDTO);
    product.setId(productId);

    doReturn(just(product)).when(productService).findById(productId);

    webClient.get().uri("/products/" + productId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.name").isEqualTo(productDTO.name())
        .jsonPath("$.id").isEqualTo(productId);

    verify(productService, times(1)).findById(productId);
  }

  @Test
  public void shouldFindAllProducts() {
    ProductDTO productDTO = getProductDTO();
    String productId = UUID.randomUUID().toString();

    Product product = productMapper.toDomain(productDTO);
    product.setId(productId);

    doReturn(Flux.just(product)).when(productService).findAll();

    webClient.get().uri("/products")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.length()").isEqualTo(1)
        .jsonPath("$[0].name").isEqualTo(productDTO.name())
        .jsonPath("$[0].id").isEqualTo(productId);

    verify(productService, times(1)).findAll();
  }

  private ProductDTO getProductDTO() {
    return new ProductDTO(
        null,
        "Product Test",
        "Category Test",
        new BigDecimal("99.88"),
        BigInteger.valueOf(999)
    );
  }

}
