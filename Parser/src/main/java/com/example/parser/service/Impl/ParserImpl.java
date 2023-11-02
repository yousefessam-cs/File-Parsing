package com.example.parser.service.Impl;

import com.example.parser.repository.TransactionRepository;
import com.example.parser.service.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ParserImpl implements Parser {
    private ValidateTransaction validateTransaction;
    private TransactionExtractor transactionExtractor;
    private TransactionRepository transactionRepository;
    private SendTransaction sendTransaction;
    private SaveTransaction saveTransaction;



    @Autowired
    public ParserImpl(ValidateTransaction validateTransaction, TransactionExtractor transactionExtractor, TransactionRepository transactionRepository, SendTransaction sendTransaction, SaveTransaction saveTransaction) {
        this.validateTransaction = validateTransaction;
        this.transactionExtractor = transactionExtractor;
        this.transactionRepository=transactionRepository;
        this.sendTransaction = sendTransaction;
        this.saveTransaction = saveTransaction;
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(ParserImpl.class);
    @Override
    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void Parser(Message transaction) {

        LOGGER.info(String.format("Received Transaction -> %s",transaction));
        String messageId = transaction.getMessageProperties().getMessageId();
        System.out.println(messageId);
        String transactionData = new String(transaction.getBody());
        long fileId = Long.parseLong(transaction.getMessageProperties().getHeaders().get("fileId").toString());
        System.out.println(fileId);
        String fileName = transaction.getMessageProperties().getHeaders().get("fileName").toString();
        System.out.println(fileName);
        try {
            String senderAccountPic=transactionExtractor.ExtractSenderAccountBic(transactionData);
            String senderAccNumber=transactionExtractor.ExtractSenderAccountNumber(transactionData);
            String  receiverAccountPic =transactionExtractor.ExtractReceiverAccountBic(transactionData);
            String recieverAccountNumber=transactionExtractor.ExtractReceiverAccountNumber(transactionData);
            BigDecimal transactionAmount=transactionExtractor.ExtractTransactionamount(transactionData);
            String transactionCurrency=transactionExtractor.ExtractTransactionCurrency(transactionData);
            LocalDateTime transactionDate=transactionExtractor.ExtractTransactionDate(transactionData);
            String transactionReference=transactionExtractor.ExtractTransactionReference(transactionData);
            boolean isTransactionAmountValid= validateTransaction.isTransactionAmountValid(transactionAmount);
            boolean isTransactionCurrencyValid=validateTransaction.isTransactionCurrencyValid(transactionCurrency);
            boolean areAccountCountriesValid= validateTransaction.areAccountCountriesValid(transactionExtractor.ExtractSenderAccountCountry(transactionData),transactionExtractor.ExtractReceiverAccountCountry(transactionData));
            boolean isTransactionReferenceDuplicate=validateTransaction.isTransactionReferenceDuplicate(transactionReference);
            if (isTransactionAmountValid && isTransactionCurrencyValid && areAccountCountriesValid && !isTransactionReferenceDuplicate) {
                // Success: Save transaction in database
                saveTransaction.saveTransaction(senderAccountPic,senderAccNumber,receiverAccountPic,recieverAccountNumber,transactionAmount,transactionCurrency,transactionDate,transactionReference, fileId,fileName);
                sendTransaction.sendTransactionsToQueue(transactionData,"success");
            } else {
                // Failure: Save transaction in DB with reason of failure
                String failureReason = "";
                if (!isTransactionAmountValid) {
                    failureReason += "Invalid transaction amount; ";
                }
                if (!isTransactionCurrencyValid) {
                    failureReason += "Invalid transaction currency; ";
                }
                if (!areAccountCountriesValid) {
                    failureReason += "Invalid account countries; ";
                }
                if (isTransactionReferenceDuplicate) {
                    failureReason += "Duplicate transaction reference; ";
                }
                saveTransaction.saveTransactionWithFailureReason(senderAccountPic,senderAccNumber,receiverAccountPic,recieverAccountNumber,transactionAmount,transactionCurrency,transactionDate,transactionReference,failureReason, fileId, fileName);
                sendTransaction.sendTransactionsToQueue(transactionData,"failed");
            }
    }catch(Exception e){
            e.getCause();
        }
    }

}
