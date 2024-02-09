package com.example.boardcamp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record GameDTO(
        @NotEmpty(message = "O nome do jogo não pode ser nulo ou vazio")
        String name,
        String image,
        @Min(value = 1, message = "O valor do stock deve ser maior que zero")
        int stockTotal,
        @Min(value = 1, message = "O valor do stock deve ser maior que zero")
        int pricePerDay) {
}

