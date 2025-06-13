package dev.nmarulo.record_structure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class RecordStructureApiApplication {
    
    public static void main(String[] args) {
        createDataDirectoryIfNotExists();
        
        SpringApplication.run(RecordStructureApiApplication.class, args);
    }
    
    private static void createDataDirectoryIfNotExists() {
        try {
            final var dataPath = Paths.get("./data");
            
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                System.out.println("Directorio 'data' creado exitosamente en: " + dataPath.toAbsolutePath());
            }
        } catch (Exception e) {
            // No lanzamos excepción para permitir que Spring maneje el error de conexión
            System.err.println("Error al crear el directorio data: " + e.getMessage());
        }
    }
    
}
