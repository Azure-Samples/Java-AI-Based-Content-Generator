# Use an official JDK base image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the WAR file into the container
COPY target/backend.war /app/backend.war

# Add environment variables
ENV AZURE_COSMOS_MONGODB_CONNECTION_STRING="<your_cosmosdb_connection_string>"
ENV AZURE_STORAGE_CONNECTION_STRING="<your_storage_connection_string>"
ENV MONGODB_DATABASE_NAME="<your_mongodb_database_name>"
ENV AZURE_STORAGE_CONTAINER_NAME="<your_storage_container_name>"

# Expose the port the app runs on
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "/app/backend.war"]
