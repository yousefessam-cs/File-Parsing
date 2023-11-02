package com.example.reciever.service.Impl;

import com.example.reciever.constants.AppConfig;
import com.example.reciever.service.FileSaver;
import com.example.reciever.service.ReceiveTransaction;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveTransactionImpl implements ReceiveTransaction {



    private AppConfig appConfig;
    private FileSaver fileSaver;

    @Autowired
    public ReceiveTransactionImpl(AppConfig appConfig, FileSaver fileSaver) {
        this.appConfig = appConfig;
        this.fileSaver = fileSaver;
    }
    private static final Logger LOGGER= LoggerFactory.getLogger(ReceiveTransactionImpl.class);
    @Override
    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void ReceiveTransaction(Message transaction) {
        LOGGER.info(String.format("Received Transaction -> %s", transaction));
        String messageId = transaction.getMessageProperties().getMessageId();
        String messageData = new String(transaction.getBody());
        System.out.println(messageData + messageId);

        if (messageId.equalsIgnoreCase("success")) {
            fileSaver.saveSuccessFile(messageData);
        } else {
            fileSaver.saveFailedFile(messageData);
        }
    }

}
