# Use an official Node.js runtime as a parent image
FROM mcr.microsoft.com/devcontainers/javascript-node:latest AS build

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React application
RUN npm run build

# Use an official Nginx image to serve the application
FROM mcr.microsoft.com/cbl-mariner/base/nginx:1

# Copy the built React application from the previous stage
COPY --from=build /app/build /usr/share/nginx/html

# Set environment variables for the Nginx container
ARG REACT_APP_SERVICE_BASE_URL
ARG REACT_APP_CLIENT_ID
ARG REACT_APP_CONTENT_GENERATOR_ENDPOINT
ARG REACT_APP_SERVICE_ACCESS_KEY

# Set environment variables for the Nginx container
ENV REACT_APP_SERVICE_BASE_URL=${REACT_APP_SERVICE_BASE_URL}
ENV REACT_APP_CLIENT_ID=${REACT_APP_CLIENT_ID}
ENV REACT_APP_CONTENT_GENERATOR_ENDPOINT=${REACT_APP_CONTENT_GENERATOR_ENDPOINT}
ENV REACT_APP_SERVICE_ACCESS_KEY=${REACT_APP_SERVICE_ACCESS_KEY}

# Expose port 80 to the outside world
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
