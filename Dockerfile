# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and the pom.xml file into the container
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download all dependencies to the Maven cache
RUN ./mvnw dependency:go-offline -B

# Copy the source code into the container
COPY src src

# Package the application
RUN ./mvnw clean package -DskipTests

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/portfolio-tracker-backend-0.0.1-SNAPSHOT.jar"]
