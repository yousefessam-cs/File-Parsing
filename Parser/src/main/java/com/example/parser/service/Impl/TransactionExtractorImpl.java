package com.example.parser.service.Impl;

import com.example.parser.service.TransactionExtractor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionExtractorImpl implements TransactionExtractor {
     public String ExtractSenderAccountBic(String transaction){
         if (transaction == null || transaction.isEmpty()) {
             return "Invalid Transaction";
         }else {
             String SenderAccountBic = transaction.substring(2, 13);
             return SenderAccountBic;
         }
    }
    public String ExtractSenderAccountCountry(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String SenderAccountCountry = transaction.substring(5, 7);
            return SenderAccountCountry;
        }
    }
    public String ExtractSenderAccountNumber(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String SenderAccountNumber = transaction.substring(13, 25);
            return SenderAccountNumber;
        }
    }
    public String ExtractReceiverAccountBic(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String ReceiverAccountBic = transaction.substring(25, 35);
            return ReceiverAccountBic;
        }
    }
    public String ExtractReceiverAccountCountry(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String ReceiverAccountBic = transaction.substring(27, 29);
            return ReceiverAccountBic;
        }
    }
    public String ExtractReceiverAccountNumber(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String ReceiverAccountNumber = transaction.substring(35, 47);
            return ReceiverAccountNumber;
        }
    }
    public BigDecimal ExtractTransactionamount(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            System.out.println("Invalid Transaction");
        }else {
            BigDecimal transactionAmount = new BigDecimal(transaction.substring(47, 59));
            return transactionAmount;
        }
        return null;
    }
    public String ExtractTransactionCurrency(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String transactionCurrency = transaction.substring(59, 62);
            return transactionCurrency;
        }
    }
    public LocalDateTime ExtractTransactionDate(String transaction) throws ParseException {
        if (transaction == null || transaction.isEmpty()) {
            System.out.println("Invalid Transaction");
        }else {
            String transactionDateStr = transaction.substring(62, 76);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime transactionDate = LocalDateTime.parse(transactionDateStr, formatter);
            return transactionDate;
        }
        return null;
    }
    public String ExtractTransactionReference(String transaction){
        if (transaction == null || transaction.isEmpty()) {
            return "Invalid Transaction";
        }else {
            String transactionReference = transaction.substring(76, 84);
            return transactionReference;
        }
    }



}
