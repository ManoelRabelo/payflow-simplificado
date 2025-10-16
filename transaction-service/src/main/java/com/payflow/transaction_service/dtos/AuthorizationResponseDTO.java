package com.payflow.transaction_service.dtos;

public record AuthorizationResponseDTO(
        String status,
        AuthorizationDataDTO data
) {
    public record AuthorizationDataDTO(
            Boolean authorization
    ) {
    }
}
