# syntax=docker/dockerfile:1.7

FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends unzip \
    && rm -rf /var/lib/apt/lists/*

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw

RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -DskipTests dependency:go-offline

COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/quarkus-app/ ./

EXPOSE 8080

CMD ["java", "-jar", "quarkus-run.jar"]
