package com.example.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AdminNotificationListener {

    @JmsListener(destination = "${app.jms.admin-queue}")
    public void onAdminMessage(String message) {
        System.out.println("ADMIN JMS: " + message);
    }
}
