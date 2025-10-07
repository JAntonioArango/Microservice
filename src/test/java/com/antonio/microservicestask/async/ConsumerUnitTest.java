package com.antonio.microservicestask.async;

import com.antonio.microservicestask.entities.TrainerWorkload;
import com.antonio.microservicestask.services.WorkloadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumerUnitTest {

    @Mock
    private WorkloadService workloadService;

    @InjectMocks
    private Consumer consumer;

    @Test
    void onMessage_ValidMessage_ShouldProcessSuccessfully() {
        String message = "TrainerWorkload[username=test, firstName=John, lastName=Doe, active=true, trainingDate=2024-01-15T10:30:00Z, trainingDuration=60]";

        consumer.onMessage(message);

        ArgumentCaptor<TrainerWorkload> captor = ArgumentCaptor.forClass(TrainerWorkload.class);
        verify(workloadService).saveWorkload(captor.capture());
        
        TrainerWorkload saved = captor.getValue();
        assertEquals("testasync", saved.getUsername());
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
        assertTrue(saved.isActive());
        assertEquals(60, saved.getTrainingDuration());
    }

    @Test
    void onMessage_NullMessage_ShouldHandleGracefully() {
        consumer.onMessage(null);
        verify(workloadService, never()).saveWorkload(any());
    }

    @Test
    void onMessage_EmptyMessage_ShouldHandleGracefully() {
        consumer.onMessage("");
        verify(workloadService, never()).saveWorkload(any());
    }

    @Test
    void onMessage_InvalidFormat_ShouldHandleGracefully() {
        consumer.onMessage("invalid message format");
        verify(workloadService, never()).saveWorkload(any());
    }
}