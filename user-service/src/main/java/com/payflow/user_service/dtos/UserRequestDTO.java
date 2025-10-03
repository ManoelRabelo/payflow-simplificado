package com.payflow.user_service.dtos;

import com.payflow.user_service.entities.UserType;

import java.math.BigDecimal;

public record UserRequestDTO(String name, String document, String email, String password, BigDecimal balance,
                             UserType type) {
}
