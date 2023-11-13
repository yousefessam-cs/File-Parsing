package com.example.parser.service.Impl;

import com.example.parser.repository.TransactionRepository;
import com.example.parser.service.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.LocalDateTime;

@Service
@Component
public class ParserImpl implements Parser {
    private ValidateTransaction validateTransaction;
    private TransactionExtractor transactionExtractor;
    private TransactionRepository transactionRepository;
    private SendTransaction sendTransaction;
    private SaveTransaction saveTransaction;
    private DecryptTransactionDataImpl decryptTransactionData;
    private PrivateKeyLoaderImpl privateKeyLoader;



    @Autowired
    public ParserImpl(ValidateTransaction validateTransaction, TransactionExtractor transactionExtractor, TransactionRepository transactionRepository, SendTransaction sendTransaction, SaveTransaction saveTransaction, DecryptTransactionDataImpl decryptTransactionData, PrivateKeyLoaderImpl privateKeyLoader) {
        this.validateTransaction = validateTransaction;
        this.transactionExtractor = transactionExtractor;
        this.transactionRepository=transactionRepository;
        this.sendTransaction = sendTransaction;
        this.saveTransaction = saveTransaction;
        this.decryptTransactionData = decryptTransactionData;
        this.privateKeyLoader = privateKeyLoader;
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(ParserImpl.class);
    @Override
    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void Parser(Message transaction) {
        LOGGER.info(String.format("Received Transaction -> %s",transaction));
        String messageId = transaction.getMessageProperties().getMessageId();
        System.out.println(messageId);

        String encryptedData = new String(transaction.getBody());
        PrivateKey privateKey = privateKeyLoader.loadPrivateKeyFromFile("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\private.pem");
        String decryptedTransactionData =decryptTransactionData.decrypt(encryptedData,privateKey);
        long fileId = Long.parseLong(transaction.getMessageProperties().getHeaders().get("fileId").toString());
        System.out.println(fileId);
        String fileName = transaction.getMessageProperties().getHeaders().get("fileName").toString();
        System.out.println(fileName);
        try {
            String senderAccountPic=transactionExtractor.ExtractSenderAccountBic(decryptedTransactionData);
            String senderAccNumber=transactionExtractor.ExtractSenderAccountNumber(decryptedTransactionData);
            String  receiverAccountPic =transactionExtractor.ExtractReceiverAccountBic(decryptedTransactionData);
            String recieverAccountNumber=transactionExtractor.ExtractReceiverAccountNumber(decryptedTransactionData);
            BigDecimal transactionAmount=transactionExtractor.ExtractTransactionamount(decryptedTransactionData);
            String transactionCurrency=transactionExtractor.ExtractTransactionCurrency(decryptedTransactionData);
            LocalDateTime transactionDate=transactionExtractor.ExtractTransactionDate(decryptedTransactionData);
            String transactionReference=transactionExtractor.ExtractTransactionReference(decryptedTransactionData);
            boolean isTransactionAmountValid= validateTransaction.isTransactionAmountValid(transactionAmount);
            boolean isTransactionCurrencyValid=validateTransaction.isTransactionCurrencyValid(transactionCurrency);
            boolean areAccountCountriesValid= validateTransaction.areAccountCountriesValid(transactionExtractor.ExtractSenderAccountCountry(decryptedTransactionData),transactionExtractor.ExtractReceiverAccountCountry(decryptedTransactionData));
            boolean isTransactionReferenceDuplicate=validateTransaction.isTransactionReferenceDuplicate(transactionReference);
            if (isTransactionAmountValid && isTransactionCurrencyValid && areAccountCountriesValid && !isTransactionReferenceDuplicate) {
                // Success: Save transaction in database
                saveTransaction.saveTransaction(senderAccountPic,senderAccNumber,receiverAccountPic,recieverAccountNumber,transactionAmount,transactionCurrency,transactionDate,transactionReference, fileId,fileName);
                sendTransaction.sendTransactionsToQueue(decryptedTransactionData,"success",transactionReference);
               // List<Transaction> transactions = transactionRepository.getTransactionsByFileName(fileName);
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
                sendTransaction.sendTransactionsToQueue(decryptedTransactionData,"failed",transactionReference);
            }
    }catch(Exception e){
            e.getCause();
        }
    }

}
