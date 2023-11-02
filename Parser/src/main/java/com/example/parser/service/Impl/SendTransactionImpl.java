package com.example.parser.service.Impl;

import com.example.parser.service.SendTransaction;
import org.aspectj.lang.annotation.Before;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendTransactionImpl implements SendTransaction {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.recievequeue.name}")
    private  String queue ;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public SendTransactionImpl(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendTransactionsToQueue(String transaction, String status) {
        if (transaction.isEmpty() || status == null || status.isEmpty()) {
            System.out.println("Invalid Transaction");
        }
        else if(status.equalsIgnoreCase("success")) {
            sendSuccessMessage(transaction);
        }else if(status.equalsIgnoreCase("failed")){
            sendFailedMessage(transaction);
        }
    }
    private void sendFailedMessage(String transaction) {
        rabbitTemplate.convertAndSend(exchange, queue, transaction, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setMessageId("failed");
            System.out.println("Failed Message Sent");
            return message;
        });
    }
    private void sendSuccessMessage(String transaction) {
        rabbitTemplate.convertAndSend(exchange, queue, transaction, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setMessageId("success");
            System.out.println("Succeeded Message Sent");
            return message;
        });
    }
}

