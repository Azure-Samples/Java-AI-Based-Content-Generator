# Use an official Node.js runtime as a parent image
FROM node:18 AS build

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
FROM nginx:alpine

# Copy the built React application from the previous stage
COPY --from=build /app/build /usr/share/nginx/html


# Set environment variables for the Nginx container
ENV REACT_APP_SERVICE_BASE_URL=https://dev-content-generator-middleware-aegvcjdjbkaqfch8.eastus-01.azurewebsites.net
ENV REACT_APP_CLIENT_ID=c23e9e28-22a3-4f63-880f-eaa88fce0503
ENV REACT_APP_CONTENT_GENERATOR_ENDPOINT=/api/v1/generate/content
ENV REACT_APP_SERVICE_ACCESS_KEY=b2dd28f8a77141d5a35baf3e2f3fcd45

# Expose port 80 to the outside world
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
