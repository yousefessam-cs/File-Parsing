package com.example.adapter.service.impl;

import com.example.adapter.entity.FileData;
import com.example.adapter.service.TransactionsHandler;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsHandlerImpl implements TransactionsHandler {

    @Value("${rabbitmq.exchang.name}")
    private String exchange ;

    @Value("${rabbitmq.queue.name}")
    private String queue ;

    private RabbitTemplate rabbitTemplate;

    public TransactionsHandlerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<String> getTransactions(String data) {
        String[] lines = data.split("\n");
        System.out.println("getting transactions");
        // Exclude the first line as it contains the FH line
        List<String> transactions = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            transactions.add(lines[i]);
        }
        return transactions;
    }
    @Override
    public int extractNumberOfTransactions(String data) {
        System.out.println(data);
        String[] lines = data.split("\n");
        // Assume the line "FH0004" is located at index 0
        String fhLine = lines[0];
        // Extract the transaction count from the FH line
        try {
            int transactionCount = Integer.parseInt(fhLine.substring(2));
//        System.out.println("Getting Number Of Transactions");
            return transactionCount;
        } catch (NumberFormatException e){
            System.out.println("Invalid transaction count format");
            throw new NumberFormatException("Invalid transaction count format");
        }

    }
    public void sendTransactionsToQueue(String data,long fileId,String fileName) {
        List<String> transactions = getTransactions(data);
        for (String transaction : transactions) {
            rabbitTemplate.convertAndSend(exchange, queue, transaction, message -> {
                MessageProperties properties = message.getMessageProperties();
                properties.setMessageId("transaction");
                properties.setHeader("fileId", String.valueOf(fileId));
                properties.setHeader("fileName", fileName);
                return message;
            });
        }
    }


}
