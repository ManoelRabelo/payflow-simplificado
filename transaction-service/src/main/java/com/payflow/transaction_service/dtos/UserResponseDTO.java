package com.payflow.transaction_service.dtos;

import java.math.BigDecimal;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        BigDecimal balance,
        String type
) {
}
