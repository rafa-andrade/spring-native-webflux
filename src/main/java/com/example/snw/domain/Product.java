package com.example.snw.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Document
@Data
public class Product {

  @Id
  private String id;

  private String name;

  private String category;

  private BigDecimal price;

  private BigInteger stock;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
