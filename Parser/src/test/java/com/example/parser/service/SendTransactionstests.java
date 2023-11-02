package com.example.parser.service;

import com.example.parser.service.Impl.SendTransactionImpl;
import com.rabbitmq.client.ConnectionFactory;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assert;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SendTransactionstests {


    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private SendTransactionImpl sendTransactionImpl;
    @Autowired
    private SendTransactionImpl getSendTransactionImpl1;



    @Test
    public void testSendTransactionsToQueueSuccess() {

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String status = "success";

        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method being tested
        getSendTransactionImpl1.sendTransactionsToQueue(transaction,status);
        String output = outputStream.toString().trim();

        assertEquals("Succeeded Message Sent", output);


        // Verify that rabbitTemplate.convertAndSend method was called with the correct arguments
     // verify(getSendTransactionImpl1, times(1)).sendTransactionsToQueue(transaction,status);
    }
    @Test
    public void testSendTransactionsToQueueFailed() {

        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
        //SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000012000EGP2023081117140114efa3bf";
        String status = "failed";
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method being tested
        getSendTransactionImpl1.sendTransactionsToQueue(transaction, status);
        String output = outputStream.toString().trim();

        assertEquals("Failed Message Sent", output);


        // Verify that the convertAndSend method with MessagePostProcessor was called with the correct arguments
        //verify(rabbitTemplate, times(1)).convertAndSend(eq("MyExchange"), eq("ReceiveMQ"), eq(transaction), any(MessagePostProcessor.class));
    }
    @BeforeEach
    public void setup() {
        rabbitTemplate = new RabbitTemplate();
        sendTransactionImpl = new SendTransactionImpl(rabbitTemplate);
    }
    @Test
    public void testSendTransactionsToQueueWithEmptyTransaction() {
        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
        //SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "";
        String status = "success";
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method being tested
        sendTransactionImpl .sendTransactionsToQueue(transaction, status);

        String output = outputStream.toString().trim();

        assertEquals("Invalid Transaction", output);

        // Verify that the convertAndSend method with MessagePostProcessor was not called
       // Mockito.verify(sendTransactionImpl, Mockito.times(1)).sendTransactionsToQueue(transaction,status);
    }


    @Test
    public void testSendTransactionsToQueueWithNullStatus() {
        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
       // SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String status = null;
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        sendTransactionImpl.sendTransactionsToQueue(transaction,status);

        String output = outputStream.toString().trim();

        assertEquals("Invalid Transaction", output);

    }
    @Test
    public void testSendTransactionsToQueueWithEmptyMessageAndNullStatus() {
        // Set up the necessary data for testing
        String transaction = "";
        String status = null;

        // Call the method being tested

        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        sendTransactionImpl.sendTransactionsToQueue(transaction, status);

        // Verify the output
        String output = outputStream.toString().trim();
        assertEquals("Invalid Transaction", output);
    }




}
