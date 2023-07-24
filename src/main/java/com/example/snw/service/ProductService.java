package com.example.snw.service;

import com.example.snw.domain.Product;
import com.example.snw.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public Mono<Product> save(Product product) {
    return Mono.just(product)
        .doOnNext(p -> log.info("Saving product {}", p))
        .map(p -> {
          p.setCreatedAt(LocalDateTime.now());
          p.setUpdatedAt(LocalDateTime.now());
          return p;
        })
        .flatMap(productRepository::save);
  }

  public Mono<Product> update(String productId, Product product) {
    return productRepository
        .findById(productId)
        .switchIfEmpty(Mono.error(new NoSuchElementException()))
        .doOnNext(p -> log.info("Updating product {}", product))
        .map(p -> {
          p.setName(product.getName());
          p.setPrice(product.getPrice());
          p.setStock(product.getStock());
          p.setCategory(product.getCategory());
          p.setUpdatedAt(LocalDateTime.now());
          return p;
        })
        .flatMap(productRepository::save);
  }

  public Flux<Product> findAll() {
    return productRepository.findAll();
  }

  public Mono<Product> findById(String productId) {
    return productRepository.findById(productId);
  }

  public Mono<Void> deleteById(String productId) {
    return productRepository
        .findById(productId)
        .switchIfEmpty(Mono.error(new NoSuchElementException()))
        .doOnNext(p -> log.info("Deleting product {}", p))
        .flatMap(p -> productRepository.deleteById(productId));
  }
}