# Stage 1: Build the React application
FROM --platform=linux/amd64  mcr.microsoft.com/azurelinux/base/nodejs:20.14 as build

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json ./
RUN npm install

# Copy all files and build the application
COPY . .
RUN npm run build

# Stage 2: Serve the React app using NGINX
FROM --platform=linux/amd64  mcr.microsoft.com/azurelinux/base/nginx:1.25

COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 to serve the app
EXPOSE 80

# Start NGINX server
CMD ["nginx", "-g", "daemon off;"]
