package com.example.parser.repository;

import com.example.parser.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> getTransactionsByFileName(String fileName);
}
