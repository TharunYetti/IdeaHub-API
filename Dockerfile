# Use OpenJDK base image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy Maven files and build the app
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Run the app
CMD ["java", "-jar", "target/IdeaHub-1.0.jar"]