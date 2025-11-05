package com.antonio.microservicestask.repositories;

import com.antonio.microservicestask.entities.TrainerSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(properties = {
    "spring.data.mongodb.database=test-db",
    "spring.mongodb.embedded.version=3.5.5"
})
class TrainerSummaryRepositoryTest {

    @Autowired
    private TrainerSummaryRepository repository;

    @Test
    void findFirstByUsername_existingUsername_returnsTrainerSummary() {
        TrainerSummary summary = new TrainerSummary();
        summary.setUsername("john.doe");
        summary.setFirstName("John");
        summary.setLastName("Doe");
        summary.setActive(true);
        
        repository.save(summary);

        Optional<TrainerSummary> found = repository.findFirstByUsername("john.doe");
        assertTrue(found.isPresent());
        assertEquals("john.doe", found.get().getUsername());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void findFirstByUsername_nonExistentUsername_returnsEmpty() {
        Optional<TrainerSummary> found = repository.findFirstByUsername("nonexistent");
        assertFalse(found.isPresent());
    }

    @Test
    void save_validTrainerSummary_savesAndRetrievesSuccessfully() {
        TrainerSummary summary = new TrainerSummary();
        summary.setUsername("jane.smith");
        summary.setFirstName("Jane");
        summary.setLastName("Smith");
        summary.setActive(false);

        TrainerSummary saved = repository.save(summary);
        assertNotNull(saved.getId());

        Optional<TrainerSummary> retrieved = repository.findById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("jane.smith", retrieved.get().getUsername());
    }
}