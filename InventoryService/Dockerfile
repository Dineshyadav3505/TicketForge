# Builder stage - Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy Maven wrapper files first (for layer caching)
COPY pom.xml .
RUN apt-get update && apt-get install -y maven && \
    mvn dependency:go-offline -B && \
    apt-get clean

# Copy source and build
COPY src ./src
RUN mvnw clean package -DskipTests

# Runtime stage - lightweight Alpine JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
