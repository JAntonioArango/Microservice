package com.antonio.microservicestask.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JmsConnectionTester {

    @Autowired
    private JmsTemplate jmsTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void testJmsConnection() {
        try {
            // Test connection
            jmsTemplate.getConnectionFactory().createConnection().close();
            log.info("✅ JMS connection to Artemis successful!");
        } catch (Exception e) {
            log.error("❌ JMS connection failed: {}", e.getMessage());
            log.error("Please check:");
            log.error("1. Artemis broker is running");
            log.error("2. Port 61616 is accessible");
            log.error("3. No firewall blocking the connection");
            log.error("4. Correct broker URL configuration");
        }
    }
}