FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]