# Use a base image with JDK 17
FROM mcr.microsoft.com/openjdk/jdk:17-mariner

# Set the working directory
WORKDIR /app

# Copy the WAR file into the container
COPY target/middleware.war /app/middleware.war

# Set environment variables
ARG AZURE_KEY_VAULT_URI
ARG AZURE_CLIENT_ID
ARG AZURE_CLIENT_SECRET
ARG AZURE_TENANT_ID

# Add environment variables
ENV AZURE_KEY_VAULT_URI=${AZURE_KEY_VAULT_URI}
ENV AZURE_CLIENT_ID=${AZURE_CLIENT_ID}
ENV AZURE_CLIENT_SECRET=${AZURE_CLIENT_SECRET}
ENV AZURE_TENANT_ID=${AZURE_TENANT_ID}

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/middleware.war"]

