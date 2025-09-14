# Dockerfile
FROM openjdk:21-jdk

WORKDIR /app

# Copy the built JAR file
COPY build/libs/loans-evaluation-api-*.jar app.jar

# Expose port
EXPOSE 9091

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
