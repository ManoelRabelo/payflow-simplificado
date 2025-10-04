package com.payflow.user_service.dtos;

import com.payflow.user_service.entities.UserType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UserRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Documento é obrigatório")
        String document,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password,

        @NotNull(message = "Saldo é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "Saldo não pode ser negativo")
        BigDecimal balance,

        @NotNull(message = "Tipo de usuário é obrigatório")
        UserType type
) {
}
