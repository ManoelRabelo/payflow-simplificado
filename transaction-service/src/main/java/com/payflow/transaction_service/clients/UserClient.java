package com.payflow.transaction_service.clients;

import com.payflow.transaction_service.config.AppConfig;
import com.payflow.transaction_service.dtos.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8081",
        configuration = AppConfig.class)
public interface UserClient {

    @GetMapping("/users/{id}")
    UserResponseDTO findById(@PathVariable Long id);

    @GetMapping("/users/{id}/can-transfer")
    Boolean canTransfer(@PathVariable Long id,
                        @RequestParam BigDecimal amount);

    @PutMapping("/users/update-balance")
    Void updateBalance(@RequestParam Long senderId,
                       @RequestParam Long receiverId,
                       @RequestParam BigDecimal amount);
}
