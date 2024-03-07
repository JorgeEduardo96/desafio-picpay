FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=./target/*.jar

COPY ${JAR_FILE} /app/desafio-picpay.jar

EXPOSE 8080

CMD ["java", "-jar", "desafio-picpay.jar"]