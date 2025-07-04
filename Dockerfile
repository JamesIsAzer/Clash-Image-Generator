FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/executable.jar app.jar

EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]