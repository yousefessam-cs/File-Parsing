package com.example.parser.service;

import java.math.BigDecimal;

public interface ValidateTransaction {
    boolean isTransactionReferenceDuplicate(String transactionReference);
    boolean isTransactionAmountValid(BigDecimal transactionAmount);
    boolean isTransactionCurrencyValid(String transactionCurrency);
    boolean areAccountCountriesValid(String senderAccountCountry, String receiverAccountCountry);
}
