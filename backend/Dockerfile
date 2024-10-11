# Use an official JDK base image
FROM --platform=linux/amd64 mcr.microsoft.com/openjdk/jdk:17-mariner

# Set the working directory
WORKDIR /app

# Copy the WAR file into the container
COPY target/backend.war /app/backend.war

# Expose the port the app runs on
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "/app/backend.war"]
