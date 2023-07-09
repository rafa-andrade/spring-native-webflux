package com.example.snw.controller;

import com.example.snw.controller.dto.ProductDTO;
import com.example.snw.controller.mapper.ProductMapper;
import com.example.snw.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductMapper productMapper;

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ProductDTO> save(@RequestBody @Valid ProductDTO product) {
    return Mono.just(product)
        .map(productMapper::toDomain)
        .flatMap(productService::save)
        .map(productMapper::toDto);
  }

  @PutMapping(value = "/{productId}")
  @ResponseBody
  public Mono<ProductDTO> update(@PathVariable String productId, @RequestBody @Valid ProductDTO product) {
    return Mono.just(product)
        .map(productMapper::toDomain)
        .flatMap(p -> productService.update(productId, p))
        .map(productMapper::toDto);
  }

  @GetMapping(value = "/{productId}")
  @ResponseBody
  public Mono<ProductDTO> findById(@PathVariable String productId) {
    return productService
        .findById(productId)
        .map(productMapper::toDto);
  }

  @DeleteMapping(value = "/{productId}")
  @ResponseBody
  public Mono<Void> deleteById(@PathVariable String productId) {
    return productService
        .deleteById(productId);
  }

  @GetMapping
  @ResponseBody
  public Flux<ProductDTO> findAll() {
    return productService
        .findAll()
        .map(productMapper::toDto);
  }
}