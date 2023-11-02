package com.example.parser.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Setter
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long fileId;
    private String fileName;
    private String senderPic;
    private String senderAcc;
    private String receiverBic;
    private String receiverAcc;
    private BigDecimal amount;
    private String Currency;
    private LocalDateTime transactionDate;
    private String transactionRef;
    private String status;
    private String Reason;

}
