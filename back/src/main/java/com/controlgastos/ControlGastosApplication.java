package com.controlgastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Clase principal de la aplicación Spring Boot
 * Sistema de Control de Gastos - API REST
 * 
 * Arquitectura de tres capas:
 * - Controller: Manejo de endpoints REST
 * - Service: Lógica de negocio
 * - Repository: Acceso a MongoDB
 * 
 * Documentación API: http://localhost:8080/swagger-ui.html
 * API Docs JSON: http://localhost:8080/api-docs
 * 
 * @author Sistema Control de Gastos
 * @version 1.0.0
 */
@SpringBootApplication
@EnableMongoRepositories
public class ControlGastosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlGastosApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Control de Gastos API - Iniciada");
        System.out.println("========================================");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("API Docs: http://localhost:8080/api-docs");
        System.out.println("Base URL: http://localhost:8080/api");
        System.out.println("========================================\n");
    }
}
