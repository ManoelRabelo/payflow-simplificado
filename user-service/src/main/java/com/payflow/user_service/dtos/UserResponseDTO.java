package com.payflow.user_service.dtos;

import com.payflow.user_service.entities.UserType;

import java.math.BigDecimal;

public record UserResponseDTO(Long id, String name, String email, BigDecimal balance, UserType type) {
}
