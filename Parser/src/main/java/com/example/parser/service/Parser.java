package com.example.parser.service;
import org.springframework.amqp.core.Message;

public interface Parser {
   void Parser(Message transaction);
}
