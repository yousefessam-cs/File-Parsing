package com.example.parser.service.Impl;

import com.example.parser.service.SendTransaction;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class SendTransactionImpl implements SendTransaction {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.recievequeue.name}")
    private  String queue ;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private EncryptTransactionDataImpl encryptTransactionData;
    private final PublicKeyLoaderImpl publicKeyLoader;

    @Autowired
    public SendTransactionImpl(RabbitTemplate rabbitTemplate, PublicKeyLoaderImpl publicKeyLoader) {

        this.rabbitTemplate = rabbitTemplate;
        this.publicKeyLoader = publicKeyLoader;
    }

    @Override
    public void sendTransactionsToQueue(String transaction, String status,String transactionReference) throws Exception {
        PublicKey publicKey = publicKeyLoader.loadPublicKeyFromFile("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\public.pub");
        if (transaction.isEmpty() || status == null || status.isEmpty()) {
            System.out.println("Invalid Transaction");
        }
        else if(status.equalsIgnoreCase("success")) {
            String encryptedTransaction=encryptTransactionData.encrypt(transaction,publicKey);
            sendSuccessMessage(encryptedTransaction,transactionReference);
        }else if(status.equalsIgnoreCase("failed")){
           String encryptedTransaction= encryptTransactionData.encrypt(transaction,publicKey);
            sendFailedMessage(encryptedTransaction,transactionReference);
        }
    }
    private void sendFailedMessage(String transaction,String transactionReference) {
        rabbitTemplate.convertAndSend(exchange, queue, transaction, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setMessageId("failed");
            properties.setHeader("transactionRef", String.valueOf(transactionReference));
            System.out.println("Failed Message Sent");
            return message;
        });
    }
    private void sendSuccessMessage(String transaction,String transactionReference) {
        rabbitTemplate.convertAndSend(exchange, queue, transaction, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setMessageId("success");
            properties.setHeader("transactionRef", String.valueOf(transactionReference));
            System.out.println("Succeeded Message Sent");
            return message;
        });
    }
}

