package com.example.parser.service.Impl;

import com.example.parser.entity.Transaction;
import com.example.parser.repository.TransactionRepository;
import com.example.parser.service.SaveTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Service
public class SaveTransactionImpl implements SaveTransaction {

    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Transaction saveTransaction(String senderAccountPic, String senderAccNumber, String receiverAccountPic, String recieverAccountNumber, BigDecimal transactionAmount, String transactionCurrency, LocalDateTime transactionDate, String transactionReference,long fileId,String fileName) {
        Transaction transaction=new Transaction();
        transaction.setSenderPic(senderAccountPic);
        transaction.setSenderAcc(senderAccNumber);
        transaction.setReceiverBic(receiverAccountPic);
        transaction.setReceiverAcc(recieverAccountNumber);
        transaction.setAmount(transactionAmount);
        transaction.setCurrency(transactionCurrency);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionRef(transactionReference);
        transaction.setFileId(fileId);
        transaction.setFileName(fileName);
        transaction.setStatus("Success");
        transaction.setReason("");
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction saveTransactionWithFailureReason(String senderAccountPic, String senderAccNumber, String receiverAccountPic, String recieverAccountNumber, BigDecimal transactionAmount, String transactionCurrency, LocalDateTime transactionDate, String transactionReference, String reason,long fileId,String fileName) {
        Transaction transaction=new Transaction();
        transaction.setSenderPic(senderAccountPic);
        transaction.setSenderAcc(senderAccNumber);
        transaction.setReceiverBic(receiverAccountPic);
        transaction.setReceiverAcc(recieverAccountNumber);
        transaction.setAmount(transactionAmount);
        transaction.setCurrency(transactionCurrency);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionRef(transactionReference);
        transaction.setFileId(fileId);
        transaction.setFileName(fileName);
        transaction.setStatus("Failed");
        transaction.setReason(reason);
        transactionRepository.save(transaction);
        return transaction;
    }
}
