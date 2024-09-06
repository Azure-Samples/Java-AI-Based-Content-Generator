# Use a base image with JDK 17
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the WAR file into the container
COPY target/middleware.war /app/middleware.war

# Add environment variables
ENV AZURE_OPENAI_ENDPOINT_URL=<your-endpoint-url>
ENV AZURE_OPENAI_KEY=<your-api-key>
ENV BACKEND_SERVICE_BASE_URL=<your-backend-service-url>
ENV BACKEND_SERVICE_PRODUCT_ENDPOINT=/api/v1/products
ENV BACKEND_SERVICE_ACCESS_KEY=<your-access-key>

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/middleware.war"]
