# Use a base image with JDK 17
FROM --platform=linux/amd64 mcr.microsoft.com/openjdk/jdk:17-mariner

# Set the working directory
WORKDIR /app

# Copy the WAR file into the container
COPY target/middleware.war /app/middleware.war

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/middleware.war"]

