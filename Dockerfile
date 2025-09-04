# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Give mvnw permission to run
RUN chmod +x mvnw

# Build the app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Run the jar
CMD ["java", "-jar", "target/NotesBackend-0.0.1-SNAPSHOT.jar"]
