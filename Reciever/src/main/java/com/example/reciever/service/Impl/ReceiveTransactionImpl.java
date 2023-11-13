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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class ReceiveTransactionImpl implements ReceiveTransaction {



    private AppConfig appConfig;
    private FileSaver fileSaver;

    private PrivateKeyLoaderImpl privateKeyLoader;
    private DecryptTransactionDataImpl decryptTransactionData;



    @Autowired
    public ReceiveTransactionImpl(AppConfig appConfig, FileSaver fileSaver, PrivateKeyLoaderImpl privateKeyLoader, DecryptTransactionDataImpl decryptTransactionData) {
        this.appConfig = appConfig;
        this.fileSaver = fileSaver;
        this.privateKeyLoader = privateKeyLoader;
        this.decryptTransactionData = decryptTransactionData;
    }
    private static final Logger LOGGER= LoggerFactory.getLogger(ReceiveTransactionImpl.class);
    @Override
    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void ReceiveTransaction(Message transaction) {
        LOGGER.info(String.format("Received Transaction -> %s", transaction));
        String messageId = transaction.getMessageProperties().getMessageId();
        String transactionRef = transaction.getMessageProperties().getHeaders().get("transactionRef").toString();
        String encryptedData = new String(transaction.getBody());
        PrivateKey privateKey = privateKeyLoader.loadPrivateKeyFromFile("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\private.pem");
        String decryptedTransactionData =decryptTransactionData.decrypt(encryptedData,privateKey);
        System.out.println(decryptedTransactionData + messageId);

        if (messageId.equalsIgnoreCase("success")) {
            fileSaver.saveSuccessFile(decryptedTransactionData,transactionRef);
        } else {
            fileSaver.saveFailedFile(decryptedTransactionData,transactionRef);
        }
    }

}
