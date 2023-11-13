package com.example.reciever.service;

import com.example.reciever.constants.AppConfig;
import com.example.reciever.service.Impl.DecryptTransactionDataImpl;
import com.example.reciever.service.Impl.FileSaverImpl;
import com.example.reciever.service.Impl.PrivateKeyLoaderImpl;
import com.example.reciever.service.Impl.ReceiveTransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReceiveTransactionImplTest {
    @InjectMocks
    private ReceiveTransactionImpl receiveTransaction;

    @Mock
    private AppConfig appConfig;

    @Mock
    private FileSaver fileSaver;

    @Mock
    private PrivateKeyLoaderImpl privateKeyLoader;

    @Mock
    private DecryptTransactionDataImpl decryptTransactionData;

    @Test
    public void testReceiveTransactionWithSuccessMessage() throws Exception {
        // Mock the private key
        PrivateKey privateKey = mock(PrivateKey.class);
        when(privateKeyLoader.loadPrivateKeyFromFile(anyString())).thenReturn(privateKey);

        // Mock the encrypted data and its decryption
        String encryptedData = "Ymx2cnQLDVDA0R2NfIY+zAnIj1TDloNDQHrp/XenDHNiGwYNAPPJBBReuvMT/4M8ETFModG5ltFalOaZUKvGEJGec/zlxF/hL2ae41Qg2PBq1+E66Dw63U9rjtYXJedMJehDD4TjVjR3JZ5q6BsdPwg31u572/bnbwOuF3F4K+dD+wJIk07NvLhNp/0bOJvMjN91/jmyQqMbZzTQboCIHpcHb6rv+2qzTLRXtpsFSV5eWc87S7VR3jVf0uryqCLmzmfmYyz3yAckBYsu/+DM3PhP8vqcXG2FereGa9NiaUsVyPhSFAtAPS6EaRXh1P0giLGGRz34ata/Wo+Nay7QVg==";
        when(decryptTransactionData.decrypt(anyString(), eq(privateKey))).thenReturn("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36ba");

        // Mock the success message
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId("success");
        messageProperties.setHeader("transactionRef", "4efa36ba");
        Message successMessage = new Message(encryptedData.getBytes(), messageProperties);

        when(appConfig.getSuccessPath()).thenReturn("D:\\backup\\FileParsing\\Adapter\\src\\main\\resources\\Success");

        // Call the ReceiveTransaction() method with the success message
        receiveTransaction.ReceiveTransaction(successMessage);

        // Verify that the fileSaver saves the decrypted data
        verify(fileSaver).saveSuccessFile("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36ba", "4efa36ba");
    }

    @Test
    public void testReceiveTransactionWithFailureMessage() throws Exception {
        // Mock the private key
        PrivateKey privateKey = mock(PrivateKey.class);
        when(privateKeyLoader.loadPrivateKeyFromFile(anyString())).thenReturn(privateKey);

        // Mock the encrypted data and its decryption
        String encryptedData = "Pos+5lCy3nfG/omcvqTGyN17v2YKeo2RT/QNE8qN3sC1ZyljwGveoVPxty+JYlfeyuZBsnWQ3QYVGQ5en2CMYNKkPdrc32XH9Wt6AXpd8S4oiZgHAvf54xSvBOPM7RVmxsfaz5ht56SuOsurR17U1iGyO+1hv24p4ZZKLTGDxdsN4+Efbmkj0md289qwAFz5oNsKIMEoCzOHLhDV8J+JqYD47sshfYVgFffUGntIfjE/G0TjdftTFAyt0rw8/9G+6f6MFhCpdJsm80+BsKaEeEgVg/8ruzU7H8kwcZTW5HbgkNuaocFGdkXS4kyyypIzNfQwgyf1Hf";
        when(decryptTransactionData.decrypt(anyString(), eq(privateKey))).thenReturn("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001300EUR2023081117140114efa3bu");

        // Mock the failed message
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId("failed");
        messageProperties.setHeader("transactionRef", "4efa3bu");
        Message failedMessage = new Message(encryptedData.getBytes(), messageProperties);

        when(appConfig.getFailedPath()).thenReturn("D:\\backup\\FileParsing\\Adapter\\src\\main\\resources\\Failed");

        // Call the ReceiveTransaction() method with the failed message
        receiveTransaction.ReceiveTransaction(failedMessage);

        // Verify that the fileSaver saves the appropriate file for failure
        verify(fileSaver).saveFailedFile("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001300EUR2023081117140114efa3bu", "4efa3bu");
    }

//
//    private AppConfig appConfig;
//
//    private FileSaver fileSaver;
//
//    private PrivateKeyLoaderImpl privateKeyLoader;
//
//    private DecryptTransactionDataImpl decryptTransactionData;
//
//    private ReceiveTransaction receiveTransaction;
//

//    @BeforeEach
//    void init() {
//        appConfig =new AppConfig();
//        appConfig.setSuccessPath("D:\\backup\\FileParsing\\Adapter\\src\\main\\resources\\Success");
//        appConfig.setFailedPath("D:\\backup\\FileParsing\\Adapter\\src\\\\main\\resources\\Failed");
//        fileSaver = new FileSaverImpl(appConfig);
//        privateKeyLoader = new PrivateKeyLoaderImpl();
//        decryptTransactionData = new DecryptTransactionDataImpl();
//        receiveTransaction = new ReceiveTransactionImpl(new AppConfig(), fileSaver, privateKeyLoader, decryptTransactionData);
//    }
//    @Test
//    public void testReceiveTransactionWithSuccessMessage() {
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setMessageId("success");
//        messageProperties.setHeader("transactionRef", "4efa36ba");
//        Message successMessage = new Message("Ymx2cnQLDVDA0R2NfIY+zAnIj1TDloNDQHrp/XenDHNiGwYNAPPJBBReuvMT/4M8ETFModG5ltFalOaZUKvGEJGec/zlxF/hL2ae41Qg2PBq1+E66Dw63U9rjtYXJedMJehDD4TjVjR3JZ5q6BsdPwg31u572/bnbwOuF3F4K+dD+wJIk07NvLhNp/0bOJvMjN91/jmyQqMbZzTQboCIHpcHb6rv+2qzTLRXtpsFSV5eWc87S7VR3jVf0uryqCLmzmfmYyz3yAckBYsu/+DM3PhP8vqcXG2FereGa9NiaUsVyPhSFAtAPS6EaRXh1P0giLGGRz34ata/Wo+Nay7QVg==".getBytes(), messageProperties);
//        File successDir = new File(appConfig.getSuccessPath());
//        // Call the ReceiveTransaction() method with the success message
//        receiveTransaction.ReceiveTransaction(successMessage);
//        File[] successFiles = successDir.listFiles();
//        assertTrue(Arrays.stream(successFiles).toList().stream().map(File::getName).toList().contains("succeeded_Transaction_4efa36ba.txt"));
//
//    }
//    @Test
//    public void testReceiveTransactionWithFailureMessage() {
//
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setMessageId("failed");
//        messageProperties.setHeader("transactionRef", "4efa3bu");
//        Message failedMessage = new Message("Pos+5lCy3nfG/omcvqTGyN17v2YKeo2RT/QNE8qN3sC1ZyljwGveoVPxty+JYlfeyuZBsnWQ3QYVGQ5en2CMYNKkPdrc32XH9Wt6AXpd8S4oiZgHAvf54xSvBOPM7RVmxsfaz5ht56SuOsurR17U1iGyO+1hv24p4ZZKLTGDxdsN4+Efbmkj0md289qwAFz5oNsKIMEoCzOHLhDV8J+JqYD47sshfYVgFffUGntIfjE/G0TjdftTFAyt0rw8/9G+6f6MFhCpdJsm80+BsKaEeEgVg/8ruzU7H8kwcZTW5HbgkNuaocFGdkXS4kyyypIzNfQwgyf1HfQcDPF8uLWXhA==".getBytes(), messageProperties);
//        File failedDir = new File(appConfig.getFailedPath());
//        // Call the ReceiveTransaction() method with the success message
//        receiveTransaction.ReceiveTransaction(failedMessage);
//        File[] successFiles = failedDir.listFiles();
//        assertTrue(Arrays.stream(successFiles).toList().stream().map(File::getName).toList().contains("Failed_transaction_4efa3bu.txt"));
//    }
//




}

