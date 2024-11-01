package br.com.desafio.infrastructure.service;

public interface SqsQueueService {
    void sendMessage(String messageBody);
}