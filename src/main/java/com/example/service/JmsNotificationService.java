package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsNotificationService {

    private final JmsTemplate jmsTemplate;
    private final String adminQueue;
    private final String ordersQueue;

    public JmsNotificationService(
            JmsTemplate jmsTemplate,
            @Value("${app.jms.admin-queue}") String adminQueue,
            @Value("${app.jms.orders-queue}") String ordersQueue) {
        this.jmsTemplate = jmsTemplate;
        this.adminQueue = adminQueue;
        this.ordersQueue = ordersQueue;
    }

    public void sendAdmin(String message) {
        jmsTemplate.convertAndSend(adminQueue, message);
    }

    public void sendOrder(String message) {
        jmsTemplate.convertAndSend(ordersQueue, message);
    }
}
