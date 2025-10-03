package com.payflow.transaction_service.clients;

import com.payflow.transaction_service.dtos.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "user-Service", url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserResponseDTO findById(@PathVariable Long id);

    @GetMapping("/users/{id}/can-transfer")
    public Boolean canTransfer(@PathVariable Long id,
                               @RequestParam BigDecimal amount);

    @PutMapping("/users/update-balance")
    public Void updateBalance(@RequestParam Long senderId,
                              @RequestParam Long receiverId,
                              @RequestParam BigDecimal amount);
}
