package com.payflow.user_service.mappers;

import com.payflow.user_service.dtos.UserRequestDTO;
import com.payflow.user_service.dtos.UserResponseDTO;
import com.payflow.user_service.entities.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();

        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setBalance(dto.balance());
        user.setType(dto.type());

        return user;
    }

    public static UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance(),
                user.getType()
        );
    }
}
