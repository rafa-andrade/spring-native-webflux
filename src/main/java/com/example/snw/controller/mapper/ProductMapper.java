package com.example.snw.controller.mapper;

import com.example.snw.domain.Product;
import com.example.snw.controller.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

  public Product toDomain(ProductDTO productDTO) {
    final Product product = new Product();
    product.setCategory(productDTO.category());
    product.setStock(productDTO.stock());
    product.setName(productDTO.name());
    product.setPrice(productDTO.price());
    return product;
  }

  public ProductDTO toDto(Product product) {
    return new ProductDTO(
        product.getId(),
        product.getName(),
        product.getCategory(),
        product.getPrice(),
        product.getStock()
    );
  }
}