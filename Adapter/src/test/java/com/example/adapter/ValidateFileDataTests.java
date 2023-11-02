package com.example.adapter;

import com.example.adapter.service.TransactionsHandler;
import com.example.adapter.service.ValidateFileData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValidateFileDataTests {
    @Autowired
    private ValidateFileData validateFileData;
    private String  validData="FH0004\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a7\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76ab\n";

    private String invalidData="FH0004\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP202301012314014efa36bf\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP2023081117140114efa3bf\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401447fc3a\n" +
            "FTCIBEGCX0012123456789101NBEGCX0013123456789102000000120000EGP20230101231401c4fa76a\n";


    @Test
    void testValidateTransactionLength_WithValidLength(){
        //boolean expectedResult = true;

        // Act
        boolean actualResult = validateFileData.validateTransactionLength(validData);

        // Assert
        assertTrue(actualResult);

    }
    @Test
    void testValidateTransactionLength_WithInvalidLength(){
        //boolean expectedResult = true;

        boolean actualResult = validateFileData.validateTransactionLength(invalidData);

        // Assert
        assertFalse(actualResult);

    }

}
