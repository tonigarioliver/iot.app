# Stage 1: Build the Java application
FROM maven:3.8.1-openjdk-17 as build
WORKDIR /app
COPY . /app
RUN mvn clean install  # Use the Maven build command

# Stage 2: Create the final image
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/iot.app-0.0.1-SNAPSHOT.jar .

EXPOSE 8085

CMD ["java", "-jar", "iot.app-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev-embedded"]
