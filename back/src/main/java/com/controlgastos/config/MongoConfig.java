package com.controlgastos.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.concurrent.TimeUnit;

/**
 * Configuración optimizada de MongoDB para MongoDB Atlas
 * 
 * Esta configuración incluye:
 * - Pool de conexiones optimizado para Atlas
 * - Timeouts configurados
 * - Manejo de conexiones SSL
 * - Retry automático de escrituras
 * 
 * @author CA$HIFY Team
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    /**
     * Configuración del cliente MongoDB optimizada para Atlas
     * 
     * Incluye:
     * - Connection pooling (100 conexiones máximo, 10 mínimo)
     * - Timeouts de conexión y lectura (10 segundos)
     * - Gestión del ciclo de vida de conexiones
     */
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                // Configuración del pool de conexiones
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(100)                                    // Máximo 100 conexiones simultáneas
                        .minSize(10)                                     // Mínimo 10 conexiones en el pool
                        .maxWaitTime(2, TimeUnit.SECONDS)               // Espera máxima por una conexión
                        .maxConnectionLifeTime(30, TimeUnit.MINUTES)    // Vida máxima de una conexión
                        .maxConnectionIdleTime(10, TimeUnit.MINUTES)    // Tiempo de inactividad antes de cerrar
                )
                // Configuración de timeouts de socket
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(10, TimeUnit.SECONDS)           // Timeout de conexión inicial
                        .readTimeout(10, TimeUnit.SECONDS)              // Timeout de lectura de datos
                )
                // Configuración del cluster (para Atlas)
                .applyToClusterSettings(builder -> builder
                        .serverSelectionTimeout(5, TimeUnit.SECONDS)    // Timeout de selección de servidor
                )
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    /**
     * Bean de MongoTemplate con configuración personalizada
     * Elimina el campo "_class" que MongoDB agrega por defecto
     */
    @Bean
    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, 
                                       MappingMongoConverter converter) {
        return new MongoTemplate(databaseFactory, converter);
    }

    /**
     * Configuración del converter para eliminar el campo "_class"
     * Este campo es agregado automáticamente por Spring Data MongoDB
     * pero no es necesario y ocupa espacio
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory,
                                                       MongoMappingContext context) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);
        
        // Eliminar el campo "_class" de los documentos
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return converter;
    }
}
