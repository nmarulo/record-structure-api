package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStructureRes {
    
    private List<StructuredRecord> structuredRecords;
    
    @Data
    public static class StructuredRecord {
        
        /**
         * Identificador del registro.
         */
        private String id;
        
        private Integer order;
        
        /**
         * Valor del campo.
         */
        private Object value;
        
        private FieldType type;
        
    }
    
}
