package com.example.boardcamp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RentalDTO(
        @NotNull(message = "customerId não pode ser nulo")
        Long customerId,
        @NotNull(message = "gameId não pode ser nulo")
        Long gameId,
        @Min(value = 1, message = "daysRented deve ser maior que 0")
        int daysRented
) {
}
