FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY ./mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -DskipTests

COPY src ./src

RUN  ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre AS runner

WORKDIR /app

COPY --from=builder /app/target/Bloggy-Backend-0.0.1-SNAPSHOT.jar app.jar

CMD [ "java","-jar","app.jar" ]