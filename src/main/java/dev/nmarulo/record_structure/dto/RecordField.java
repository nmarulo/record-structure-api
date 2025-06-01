package dev.nmarulo.record_structure.dto;

import lombok.Data;

/**
 * Detalles de un campo del registro.
 */
@Data
public class RecordField {
    
    /**
     * Identificador del campo.
     */
    private String id;
    
    private Integer order;
    
    /**
     * Longitud del campo.
     */
    private Integer length;
    
    private FieldType type;
    
    private FieldFormat format;
    
}
