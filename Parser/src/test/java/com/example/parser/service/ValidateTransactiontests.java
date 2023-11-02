package com.example.parser.service;

import com.example.parser.entity.Transaction;
import com.example.parser.repository.TransactionRepository;
import com.example.parser.service.Impl.ValidateTransactionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ValidateTransactiontests {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ValidateTransactionImpl validateTransaction;

    @Test
    public void testIsTransactionReferenceDuplicate_WhenDuplicateTransactionExists() {
        String transactionReference = "4efa36bf";

        // Create a list of transactions with the desired duplicate reference
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionRef(transactionReference);
        transactions.add(transaction);

        // Mock the transaction repository to return the list of transactions
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        boolean result = validateTransaction.isTransactionReferenceDuplicate(transactionReference);

        // Assert
        Assertions.assertTrue(result);
        Mockito.verify(transactionRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void testIsTransactionReferenceDuplicate_WhenDuplicateTransactionDoesNotExist() {
        // Arrange
        String transactionReference = "4efa36bf";

        // Create a list of transactions without the duplicate reference
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionRef("87654321");
        transactions.add(transaction);

        // Mock the transaction repository to return the list of transactions
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        boolean result = validateTransaction.isTransactionReferenceDuplicate(transactionReference);

        // Assert
        Assertions.assertFalse(result);
        Mockito.verify(transactionRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void testIsTransactionAmountValid_WhenTransactionAmountLessThanMaxAmount() {
        // Arrange
        BigDecimal transactionAmount = new BigDecimal("1000");
        BigDecimal maxAmount = new BigDecimal("1500");
        // Act
        boolean result = validateTransaction.isTransactionAmountValid(transactionAmount);

        // Assert
        Assertions.assertTrue(result);
        Assertions.assertEquals(-1, transactionAmount.compareTo(maxAmount));
    }
    @Test
    public void testIsTransactionAmountValid_WhenTransactionAmountEqualToMaxAmount() {
        // Arrange
        BigDecimal transactionAmount = new BigDecimal("1500");
        BigDecimal maxAmount = new BigDecimal("1500");

        // Act
        boolean result = validateTransaction.isTransactionAmountValid(transactionAmount);

        // Assert
        Assertions.assertFalse(result);
        Assertions.assertEquals(0, transactionAmount.compareTo(maxAmount));
    }
    @Test
    public void testIsTransactionAmountValid_WhenTransactionAmountGreaterThanMaxAmount() {
        // Arrange
        BigDecimal transactionAmount = new BigDecimal("2000");
        BigDecimal maxAmount = new BigDecimal("1500");

        // Act
        boolean result = validateTransaction.isTransactionAmountValid(transactionAmount);

        // Assert
        Assertions.assertFalse(result);
        Assertions.assertEquals(1, transactionAmount.compareTo(maxAmount));
    }

    @Test
    public void testIsTransactionCurrencyValid_WhenTransactionCurrencyIsEGP() {
        // Arrange
        String transactionCurrency = "EGP";
        // Act
        boolean result = validateTransaction.isTransactionCurrencyValid(transactionCurrency);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsTransactionCurrencyValid_WhenTransactionCurrencyIsEgp() {
        // Arrange
        String transactionCurrency = "Egp";
        // Act
        boolean result = validateTransaction.isTransactionCurrencyValid(transactionCurrency);
        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsTransactionCurrencyValid_WhenTransactionCurrencyIsNotEGP() {
        // Arrange
        String transactionCurrency = "USD";
        // Act
        boolean result = validateTransaction.isTransactionCurrencyValid(transactionCurrency);

        // Assert
        Assertions.assertFalse(result);
    }
    @Test
    public void testAreAccountCountriesValid_WhenSenderAndReceiverCountriesAreEG() {
        // Arrange
        String senderAccountCountry = "EG";
        String receiverAccountCountry = "EG";
        // Act
        boolean result = validateTransaction.areAccountCountriesValid(senderAccountCountry, receiverAccountCountry);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testAreAccountCountriesValid_WhenSenderCountryIsEGAndReceiverCountryIsNotEG() {
        // Arrange
        String senderAccountCountry = "EG";
        String receiverAccountCountry = "US";

        // Act
        boolean result = validateTransaction.areAccountCountriesValid(senderAccountCountry, receiverAccountCountry);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testAreAccountCountriesValid_WhenSenderCountryIsNotEGAndReceiverCountryIsEG() {
        // Arrange
        String senderAccountCountry = "US";
        String receiverAccountCountry = "EG";
        // Act
        boolean result = validateTransaction.areAccountCountriesValid(senderAccountCountry, receiverAccountCountry);
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testAreAccountCountriesValid_WhenSenderAndReceiverCountriesAreNotEG() {
        // Arrange
        String senderAccountCountry = "US";
        String receiverAccountCountry = "CA";

        // Act
        boolean result = validateTransaction.areAccountCountriesValid(senderAccountCountry, receiverAccountCountry);

        // Assert
        Assertions.assertFalse(result);
    }



}

