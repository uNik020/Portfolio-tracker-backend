# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and necessary files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give executable permission to the Maven wrapper
RUN chmod +x mvnw

# Download dependencies to the Maven cache
RUN ./mvnw dependency:go-offline -B

# Copy the source code into the container
COPY src src

# Copy the pre-built JAR file into the container
COPY target/portfolio-tracker-backend-0.0.1-SNAPSHOT.jar app.jar

# Package the application
RUN ./mvnw clean package -DskipTests

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/portfolio-tracker-backend-0.0.1-SNAPSHOT.jar"]
