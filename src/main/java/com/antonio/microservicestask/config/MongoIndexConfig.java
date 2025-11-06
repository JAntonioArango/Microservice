package com.antonio.microservicestask.config;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(MongoTemplate.class)
public class MongoIndexConfig implements CommandLineRunner {

    private static final String COLLECTION_TRAINER_SUMMARIES = "trainer_summaries";
    private static final String FIELD_FIRST_NAME = "firstName";
    private static final String FIELD_LAST_NAME = "lastName";

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        final IndexOperations indexOperations = mongoTemplate.indexOps(COLLECTION_TRAINER_SUMMARIES);
        final CompoundIndexDefinition nameIndex = buildNameIndex();
        indexOperations.ensureIndex(nameIndex);
    }

    private static CompoundIndexDefinition buildNameIndex() {
        return new CompoundIndexDefinition(
                new Document(FIELD_FIRST_NAME, 1)
                        .append(FIELD_LAST_NAME, 1)
        );
    }
}
