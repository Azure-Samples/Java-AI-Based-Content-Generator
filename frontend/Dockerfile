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


# Expose port 80 to the outside world
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
