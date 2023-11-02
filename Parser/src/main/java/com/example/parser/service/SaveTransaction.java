package com.example.parser.service;

import com.example.parser.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaveTransaction {
    Transaction saveTransaction(String senderAccountPic, String senderAccNumber, String receiverAccountPic, String recieverAccountNumber, BigDecimal transactionAmount, String transactionCurrency, LocalDateTime transactionDate, String transactionReference,long fileId,String fileName);
    Transaction saveTransactionWithFailureReason(String senderAccountPic,String senderAccNumber,String receiverAccountPic,String recieverAccountNumber,BigDecimal transactionAmount,String transactionCurrency,LocalDateTime transactionDate, String transactionReference,String reason,long fileId,String fileName);
}
