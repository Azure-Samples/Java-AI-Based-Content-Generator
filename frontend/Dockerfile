# Stage 1: Build the React application
FROM node:18 AS build
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy all files and build the application
COPY . .
RUN npm run build

# Stage 2: Serve the React app using NGINX
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 to serve the app
EXPOSE 80

# Start NGINX server
CMD ["nginx", "-g", "daemon off;"]