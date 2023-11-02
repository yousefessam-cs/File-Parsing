package com.example.reciever.service;

import org.springframework.amqp.core.Message;

public interface ReceiveTransaction {
    void ReceiveTransaction(Message transaction);
}
