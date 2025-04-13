# Build Stage: Build with Maven
FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Copy pom and source files
COPY pom.xml mvnw mvnw.cmd ./
COPY src ./src
COPY .mvn ./.mvn

# Build the JAR file
#check if the mvnw file is exist and print the answer
RUN chmod +x ./mvnw
RUN ./mvnw clean package -Dmaven.test.skip=true

# Runtime stage - using JRE for smaller size
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /build/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Environment variables can be passed at runtime
CMD ["java", "-jar", "app.jar"]
