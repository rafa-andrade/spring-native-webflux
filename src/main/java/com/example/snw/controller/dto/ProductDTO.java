package com.example.snw.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;


public record ProductDTO (
  String id,
  @NotEmpty
  String name,
  @NotEmpty
  String category,
  @NotNull
  BigDecimal price,
  @NotNull
  BigInteger stock
) {}