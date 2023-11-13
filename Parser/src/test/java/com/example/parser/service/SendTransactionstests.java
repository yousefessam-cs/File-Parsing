package com.example.parser.service;

import com.example.parser.service.Impl.PublicKeyLoaderImpl;
import com.example.parser.service.Impl.SendTransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class SendTransactionstests {


    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private SendTransactionImpl sendTransactionImpl;
    @Autowired
    private SendTransactionImpl getSendTransactionImpl1;
    @Autowired
    private PublicKeyLoaderImpl publicKeyLoader;



    @Test
    public void testSendTransactionsToQueueSuccess() throws Exception{

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String status = "success";
        String transactionReference="4efa36bf";

        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method being tested
        getSendTransactionImpl1.sendTransactionsToQueue(transaction,status,transactionReference);
        String output = outputStream.toString().trim();

        assertEquals("Succeeded Message Sent", output);


        // Verify that rabbitTemplate.convertAndSend method was called with the correct arguments
     // verify(getSendTransactionImpl1, times(1)).sendTransactionsToQueue(transaction,status);
    }
    @Test
    public void testSendTransactionsToQueueFailed() throws Exception{

        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
        //SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000012000EGP2023081117140114efa3bf";
        String status = "failed";
        String transactionReference="4efa3bf";
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method being tested
        getSendTransactionImpl1.sendTransactionsToQueue(transaction, status,transactionReference);
        String output = outputStream.toString().trim();

        assertEquals("Failed Message Sent", output);


        // Verify that the convertAndSend method with MessagePostProcessor was called with the correct arguments
        //verify(rabbitTemplate, times(1)).convertAndSend(eq("MyExchange"), eq("ReceiveMQ"), eq(transaction), any(MessagePostProcessor.class));
    }
    @BeforeEach
    public void setup() {
        rabbitTemplate = new RabbitTemplate();
        sendTransactionImpl = new SendTransactionImpl(rabbitTemplate, publicKeyLoader);
    }
    @Test
    public void testSendTransactionsToQueueWithEmptyTransaction() throws Exception{
        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
        //SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "";
        String status = "success";
        String transactionRef="";
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        publicKeyLoader.loadPublicKeyFromFile("D:\\backup\\FileParsing\\Parser\\src\\main\\resources\\keys\\public.pem");

        // Call the method being tested
        sendTransactionImpl .sendTransactionsToQueue(transaction, status,transactionRef);

        String output = outputStream.toString().trim();

        assertEquals("Invalid Transaction", output);

        // Verify that the convertAndSend method with MessagePostProcessor was not called
       // Mockito.verify(sendTransactionImpl, Mockito.times(1)).sendTransactionsToQueue(transaction,status);
    }


    @Test
    public void testSendTransactionsToQueueWithNullStatus() throws Exception{
        // Create an instance of SendTransactionImpl with the mocked RabbitTemplate
       // SendTransactionImpl sendTransaction = new SendTransactionImpl(rabbitTemplate);

        // Set up the necessary data for testing
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String status = null;
        String transactionReference="4efa36bf";
        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        sendTransactionImpl.sendTransactionsToQueue(transaction,status,transactionReference);

        String output = outputStream.toString().trim();

        assertEquals("Invalid Transaction", output);

    }
    @Test
    public void testSendTransactionsToQueueWithEmptyMessageAndNullStatus() throws Exception{
        // Set up the necessary data for testing
        String transaction = "";
        String status = null;
        String transactionRef="";

        // Call the method being tested

        // Capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        sendTransactionImpl.sendTransactionsToQueue(transaction, status,transactionRef);

        // Verify the output
        String output = outputStream.toString().trim();
        assertEquals("Invalid Transaction", output);
    }




}
