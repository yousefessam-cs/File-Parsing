package com.example.adapter;

import com.example.adapter.service.TransactionsHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionsHandlerTests {

    private TransactionsHandler transactionsHandler;
    @Autowired
    public TransactionsHandlerTests(TransactionsHandler transactionsHandler) {
        this.transactionsHandler = transactionsHandler;
    }

    @Test
    void validateGetTransactions_checkValidScenario_ReturnsTransactionsSuccessfully(){
         String TEST_DATA = "FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n";

        List<String> expectedTransactions = new ArrayList<>();
        expectedTransactions.add("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf");
        expectedTransactions.add("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf");
        expectedTransactions.add("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7");
        expectedTransactions.add("FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab");
        // Act
        List<String> actualTransactions = transactionsHandler.getTransactions(TEST_DATA);
        //Assert
        assertEquals(expectedTransactions,actualTransactions);

    }
    @Test
    void validateGetTransactions_checkEmptyData_Returns0Transactions(){
        String data = "";

        // Act
        List<String> actualTransactions = transactionsHandler.getTransactions(data);

        // Assert
        assertEquals(0, actualTransactions.size());
    }
    @Test
    void validateGetTransactions_checkFileContainsonlyFH0004_Returns0Transactions(){
        String data = "FH0004";
        // Act
        List<String> actualTransactions = transactionsHandler.getTransactions(data);
        // Assert
        assertEquals(0, actualTransactions.size());
    }
    @Test
     void testExtractNumberOfTransactions() {
        // Arrange
        String data = "FH0004\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
                "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n";
        int expectedNumberOfTransactions = 4;

        // Act
        int actualNumberOfTransactions = transactionsHandler.extractNumberOfTransactions(data);

        // Assert
        assertEquals(expectedNumberOfTransactions, actualNumberOfTransactions);
    }
    @Test
    public void testExtractNumberOfTransactionsWith0Transactions() {
        // Arrange
        String data = "FH0000\n";
        int expectedNumberOfTransactions = 0;

        // Act
        int actualNumberOfTransactions =transactionsHandler.extractNumberOfTransactions(data);

        // Assert
        assertEquals(expectedNumberOfTransactions, actualNumberOfTransactions);
    }
    @Test
    public void testExtractNumberOfTransactionsWithNonNumericFormat() {
        // Arrange
        String data = "FHABCD\ntransaction1\ntransaction2\ntransaction3";

        Exception exception =  assertThrows(NumberFormatException.class,()->{
            transactionsHandler.extractNumberOfTransactions(data);
        });

        String  expected = "Invalid transaction count format";
        String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expected));
        // Act
//        transactionsHandler.extractNumberOfTransactions(data);
    }




}
