package com.controlgastos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 * Accesible en: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI controlGastosOpenAPI() {
        // Servidor local
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor local de desarrollo");
        
        // Información de contacto
        Contact contact = new Contact();
        contact.setName("Sistema Control de Gastos");
        contact.setEmail("controlgastos@example.com");
        
        // Licencia
        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
        
        // Información general de la API
        Info info = new Info()
                .title("Control de Gastos - API REST")
                .version("1.0.0")
                .description("API REST para sistema de control de gastos personales. " +
                        "Permite gestionar usuarios, categorías y transacciones (ingresos y gastos). " +
                        "Incluye funcionalidades de reportes y resúmenes financieros.")
                .contact(contact)
                .license(license);
        
        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
