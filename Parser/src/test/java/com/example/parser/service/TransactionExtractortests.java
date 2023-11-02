package com.example.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class TransactionExtractortests {
    @Autowired
    private TransactionExtractor transactionExtractor;

    @Test
    public void testExtractSenderAccountBic_ValidScenario_ReturnActualSenderPic() {
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String expected = "CIBEGCX0012";
        String actual = transactionExtractor.ExtractSenderAccountBic(transaction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testExtractSenderAccountCountry_ValidScenario_ReturnsActualSenderCountry() {
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String expected = "EG";
        String actual = transactionExtractor.ExtractSenderAccountCountry(transaction);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testExtractSenderAccountCountry_InValidScenario_ReturnsInvalidTransaction() {
        String transaction = "";
        String expected = "Invalid Transaction";
        String actual = transactionExtractor.ExtractSenderAccountCountry(transaction);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testExtractTransactionAmount_WhenValidTransaction_ReturnValidAmount() {
        // Arrange
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        BigDecimal expected = new BigDecimal("1200");
        // Act
        BigDecimal actual = transactionExtractor.ExtractTransactionamount(transaction);
        // Assert
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testExtractTransactionAmount_InValidScenario_ReturnsInvalidTransaction() {
        String transaction = "";
//        String expected = "Invalid Transaction";
        BigDecimal actual = transactionExtractor.ExtractTransactionamount(transaction);
        Assertions.assertNull(actual);
    }
    @Test
    public void testTransactionCurrency_InValidScenario_ReturnsInvalidTransaction() {
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String expected = "EGP";
        String actual = transactionExtractor.ExtractTransactionCurrency(transaction);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void testExtractSenderAccountCurrency_InValidScenario_ReturnsInvalidTransaction() {
        String transaction = "";
        String expected = "Invalid Transaction";
        String actual = transactionExtractor.ExtractTransactionCurrency(transaction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testExtractTransactionDate_WhenValidTransaction_ReturnCorrectDate() throws ParseException {
        // Arrange
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        LocalDateTime expected = LocalDateTime.parse("20230101231401", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Act
        LocalDateTime actual = transactionExtractor.ExtractTransactionDate(transaction);
        // Assert
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testExtractTransactionDate_WhenInValidTransaction_ReturnCorrectDate() throws ParseException{
        String transaction = "";
//        String expected = "Invalid Transaction";
        LocalDateTime actual = transactionExtractor.ExtractTransactionDate(transaction);
        Assertions.assertNull(actual);
    }
    @Test
    public void testExtractTransactionReference_WhenValidTransaction() {
        // Arrange
        String transaction = "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000001200EGP202301012314014efa36bf";
        String expected = "4efa36bf";
        // Act
        String actual = transactionExtractor.ExtractTransactionReference(transaction);

        // Assert
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testExtractTransactionReference_WhenInValidTransaction() {
        // Arrange
        String transaction = "";
        String expected = "Invalid Transaction";
        // Act
        String actual = transactionExtractor.ExtractTransactionReference(transaction);
        // Assert
        Assertions.assertEquals(expected, actual);
    }








}
