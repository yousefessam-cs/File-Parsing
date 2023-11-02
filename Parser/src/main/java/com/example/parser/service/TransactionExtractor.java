package com.example.parser.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;

public interface TransactionExtractor {
    String ExtractSenderAccountBic(String transaction);
    String ExtractSenderAccountCountry(String transaction);
    String ExtractSenderAccountNumber(String transaction);
    String ExtractReceiverAccountBic(String transaction);
    String ExtractReceiverAccountCountry(String transaction);
    String ExtractReceiverAccountNumber(String transaction);
    BigDecimal ExtractTransactionamount(String transaction);
    String ExtractTransactionCurrency(String transaction);
    LocalDateTime ExtractTransactionDate(String transaction) throws ParseException;
    String ExtractTransactionReference(String transaction);


}
