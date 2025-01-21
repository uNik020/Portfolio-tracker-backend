#maven
FROM maven:3.9.9-amazoncorretto-17-al2023 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#JDK
FROM openjdk:20-ea-14-jdk-slim-bullseye
WORKDIR /app
COPY --from=build /app/target/PortfolioBackend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","app.jar" ]