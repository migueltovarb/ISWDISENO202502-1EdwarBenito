# Dockerfile para Spring Boot Backend en Render

# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar la aplicación (saltando tests para build más rápido)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Render usa PORT env variable)
EXPOSE 8080

# Variables de entorno que se configurarán en Render
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
