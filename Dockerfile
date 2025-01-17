# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and the pom.xml file
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Grant execution permissions to the Maven wrapper
RUN chmod +x mvnw

# Package the application
RUN ./mvnw clean package -DskipTests

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/portfolio-tracker-backend-0.0.1-SNAPSHOT.jar"]
