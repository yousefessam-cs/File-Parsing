package com.example.adapter.service;

import java.util.List;

public interface TransactionsHandler {
    List<String> getTransactions(String data);

    int extractNumberOfTransactions(String data);
    void sendTransactionsToQueue(String data,long fileId,String fileName);
}
