package com.example.parser.service.Impl;

import com.example.parser.entity.Transaction;
import com.example.parser.repository.TransactionRepository;
import com.example.parser.service.TransactionExtractor;
import com.example.parser.service.ValidateTransaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ValidateTransactionImpl implements ValidateTransaction {
    private TransactionExtractor transactionExtractor;
    private TransactionRepository transactionRepository;

    public ValidateTransactionImpl(TransactionExtractor transactionExtractor, TransactionRepository transactionRepository) {
        this.transactionExtractor = transactionExtractor;
        this.transactionRepository = transactionRepository;
    }

    public boolean isTransactionReferenceDuplicate(String transactionReference) {
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionRef().equals(transactionReference)) {
                return true;
            }
        }
        return false;
        //add transactionRef
    }
    public boolean isTransactionAmountValid(BigDecimal transactionAmount) {
        BigDecimal maxAmount = new BigDecimal("1500");

        return transactionAmount.compareTo(maxAmount) < 0;
    }
    public boolean isTransactionCurrencyValid(String transactionCurrency) {
        return transactionCurrency.equalsIgnoreCase("EGP");
    }
    public boolean areAccountCountriesValid(String senderAccountCountry, String receiverAccountCountry) {
        return senderAccountCountry.equalsIgnoreCase("EG") && receiverAccountCountry.equalsIgnoreCase("EG");
    }



}
