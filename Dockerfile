# Stage 1: Build the application using Maven and Java 23
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the JAR file, skipping tests to speed up deployment
RUN mvn clean package -DskipTests

# Stage 2: Create the lightweight production image
FROM eclipse-temurin:23-jre-alpine
WORKDIR /app
# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar
# Expose the standard web port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]