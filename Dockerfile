# Use Maven with JDK 17 to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy built jar from previous step
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar (any name will work since we use *.jar)
CMD ["java", "-jar", "app.jar"]
