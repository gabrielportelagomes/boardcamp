package com.example.boardcamp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerDTO(
        @NotBlank(message = "O nome do cliente não pode ser nulo ou vazio")
        String name,
        @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
        String cpf) {
}
