package com.antonio.microservicestask.repositories;

import com.antonio.microservicestask.entities.TrainerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummary, String> {
    
    Optional<TrainerSummary> findFirstByUsername(String username);
    
    @Query("{ 'username': ?0 }")
    @Update("{ '$set': { 'firstName': ?1, 'lastName': ?2, 'active': ?3, 'years': ?4 } }")
    void updateByUsername(String username, String firstName, String lastName, Boolean active, Object years);
}