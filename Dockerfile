FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/executable.jar app.jar

COPY .env .env

RUN mkdir -p logs && chmod 755 logs

EXPOSE 34827

# Run the JAR
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]