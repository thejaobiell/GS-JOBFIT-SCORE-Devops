FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw package -DskipTests -B

RUN mv target/jobfitscore-*.jar app.jar

FROM gcr.io/distroless/java21:nonroot AS runner
WORKDIR /app

COPY --from=builder /app/app.jar app.jar

EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-jar", "app.jar"]