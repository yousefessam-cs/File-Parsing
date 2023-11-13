package com.example.parser.service;

public interface SendTransaction {
    void sendTransactionsToQueue(String transaction,String status,String transactionReference) throws Exception;
}
