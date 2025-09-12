# Stage 1: Build
FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Stage 2: Runtime
FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/build/libs/finpro-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]