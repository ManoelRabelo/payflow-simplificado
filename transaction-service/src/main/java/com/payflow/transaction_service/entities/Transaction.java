package com.payflow.transaction_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O valor da transação é obrigatório.")
    @Positive(message = "O Valor da transação deve ser maio que zero.")
    private BigDecimal amount;

    @NotNull(message = "O ID do remetente é obrigatório.")
    private Long senderId;

    @NotNull(message = "O ID do destinatário é obrigatório.")
    private Long receiverId;

    private LocalDateTime timeStamp;
}
