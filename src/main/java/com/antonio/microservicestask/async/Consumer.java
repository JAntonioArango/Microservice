package com.antonio.microservicestask.async;

import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final WorkloadService workloadService;

    @JmsListener(destination = "${app.queue}")
    public void onMessage(String msg) {
        try {
            log.info("Transaction started: JMS message processing");
            log.info("Received message: {}", msg);
            log.debug("Operation: Parsing message");
            TrainerWorkload workload = parseMessage(msg);
            log.debug("Operation: Saving workload via service");
            workloadService.saveWorkload(workload);
            log.info("Transaction completed: Successfully processed workload for username: {}", workload.getUsername());
        } catch (Exception e) {
            log.error("Transaction failed: Error processing message: {}", msg, e);
        }
    }

    private TrainerWorkload parseMessage(String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        Map<String, String> data = parseToStringFormat(msg);
        return buildTrainerWorkload(data);
    }

    private Map<String, String> parseToStringFormat(String msg) {
        Map<String, String> data = new HashMap<>();

        String content = msg.substring(msg.indexOf('[') + 1, msg.lastIndexOf(']'));
        String[] pairs = content.split(", ");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                data.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return data;
    }

    private TrainerWorkload buildTrainerWorkload(Map<String, String> data) {
        TrainerWorkload workload = new TrainerWorkload();

        workload.setUsername(data.get("username") + "async");
        workload.setFirstName(data.get("firstName"));
        workload.setLastName(data.get("lastName"));
        workload.setActive(Boolean.parseBoolean(data.get("active")));
        workload.setTrainingDate(parseDate(data.get("trainingDate")));
        workload.setTrainingDuration(Integer.parseInt(data.get("trainingDuration")));

        return workload;
    }

    private Date parseDate(String value) {
        if (value == null) return null;
        try {
            return Date.from(Instant.parse(value));
        } catch (Exception e) {
            log.error("Error parsing date: {}", value, e);
            return null;
        }
    }
}