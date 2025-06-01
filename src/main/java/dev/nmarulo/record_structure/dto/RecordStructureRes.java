package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStructureRes {
    
    /**
     * Identificador (prefijo) de la línea.
     */
    private String lineIdentifier;
    
    private List<StructuredRecord> structuredRecords;
    
    @Data
    public static class StructuredRecord {
        
        private Integer order;
        
        /**
         * Valor del campo.
         */
        private Object value;
        
        private FieldType type;
        
    }
    
}
