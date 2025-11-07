package com.antonio.microservicestask.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import com.antonio.microservicestask.cucumber.integration.IntegrationTestBeans;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestBeans.class)
@TestPropertySource(properties = {
    "eureka.client.enabled=false",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration",
    "spring.jms.enabled=false",
    "spring.data.mongodb.database=test-integration-db",
    "server.port=0"
})
public class CucumberTestConfiguration {
}