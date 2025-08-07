FROM eclipse-temurin:17-jdk-focal

COPY target/techchallenge-0.0.1-SNAPSHOT.jar /app/techchallenge.jar

CMD ["java", "-jar", "/app/techchallenge.jar"]