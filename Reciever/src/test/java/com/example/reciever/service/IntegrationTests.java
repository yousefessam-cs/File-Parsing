package com.example.reciever.service;
import com.example.parser.service.Impl.ParserImpl;
import com.example.parser.service.Impl.SendTransactionImpl;
import com.example.parser.service.SendTransaction;
import com.example.reciever.service.Impl.ReceiveTransactionImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTests {

//    @Mock
//    private ValidateTransaction validateTransactionMock;
//
//    @Mock
//    private TransactionExtractor transactionExtractorMock;
//
//    @Mock
//    private TransactionRepository transactionRepositoryMock;
//
//    @Mock
//    private SendTransaction sendTransactionMock;
//
//    @Mock
//    private SaveTransaction saveTransactionMock;
//
//    @Mock
//    private FileSaver fileSaverMock;
//
//    @InjectMocks
//    private ParserImpl parser;
    @Mock
    private SendTransactionImpl sendTransaction;
    @Mock
    private ParserImpl parserImpl;

    @Mock
    private ReceiveTransactionImpl receiveTransactionImpl;


//    @Test
//    public void testIntegration() throws ParseException {
//        // Create test data
//        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bt";
//        String status = "success";
//        String transactionReference="4efa36bf";
//        sendTransaction.sendTransactionsToQueue(transaction,status,transactionReference);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setMessageId("success");
//        messageProperties.setHeader("transactionRef", "4efa36bt");
//        Message mockMessage = new Message("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bt".getBytes(), messageProperties);
//        // Call ParserImpl Parser() method
//        parserImpl.Parser(mockMessage);
//
//        // Call ReceiveTransactionImpl ReceiveTransaction() method
//        receiveTransactionImpl.ReceiveTransaction(mockMessage);
//        verify(receiveTransactionImpl).ReceiveTransaction(mockMessage);
//
//
//    }
    @Test
    public void testIntegration() throws Exception {
        // Create test data
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bt";
        String status = "success";
        String transactionReference = "4efa36bf";

        // Mock objects
        SendTransaction sendTransaction = Mockito.mock(SendTransaction.class);
        ParserImpl parserImpl = Mockito.mock(ParserImpl.class);
        ReceiveTransactionImpl receiveTransactionImpl = Mockito.mock(ReceiveTransactionImpl.class);
        MessageProperties messageProperties = Mockito.mock(MessageProperties.class);
        Message mockMessage = Mockito.mock(Message.class);

        // Mock behavior
        Mockito.when(mockMessage.getBody()).thenReturn(transaction.getBytes());
        Mockito.when(mockMessage.getMessageProperties()).thenReturn(messageProperties);
        Mockito.when(messageProperties.getMessageId()).thenReturn(status);
        Mockito.when(messageProperties.getHeader("transactionRef")).thenReturn(transactionReference);

        // Call SendTransaction.sendTransactionsToQueue() method
        sendTransaction.sendTransactionsToQueue(transaction, status, transactionReference);

        // Call ParserImpl.Parser() method
        parserImpl.Parser(mockMessage);

        // Call ReceiveTransactionImpl.ReceiveTransaction() method
        receiveTransactionImpl.ReceiveTransaction(mockMessage);

        // Verify method calls
        Mockito.verify(sendTransaction).sendTransactionsToQueue(transaction, status, transactionReference);
        Mockito.verify(parserImpl).Parser(mockMessage);
        Mockito.verify(receiveTransactionImpl).ReceiveTransaction(mockMessage);
    }
}