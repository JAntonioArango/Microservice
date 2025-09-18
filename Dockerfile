FROM openjdk:21-jdk-slim
COPY target/MicroservicesTask-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app.jar"]