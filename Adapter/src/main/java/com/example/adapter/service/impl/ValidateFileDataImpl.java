package com.example.adapter.service.impl;
import com.example.adapter.service.ValidateFileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidateFileDataImpl implements ValidateFileData {

    private TransactionsHandlerImpl transactionsHandler;

    @Autowired
    public ValidateFileDataImpl(TransactionsHandlerImpl transactionsHandler) {
        this.transactionsHandler = transactionsHandler;
    }


    @Override
    public boolean validateTransactionLength(String data) {

        List<String> transactions = transactionsHandler.getTransactions(data);

        boolean isValid = true;

        for (String transaction : transactions) {
            if (transaction.length() != 84) {
                isValid = false;
//                System.out.println(transaction.length());
//                saveFailedTransaction(transaction);
            }
        }
        return isValid;
    }

    @Override
    public boolean validateTransactionCount(String data) {
        int expectedTransactionCount = transactionsHandler.extractNumberOfTransactions(data);
        List<String> transactions = transactionsHandler.getTransactions(data);
        System.out.println("Validating Transaction Count");
        // Validate the transaction count
        return expectedTransactionCount == transactions.size();
    }
}
