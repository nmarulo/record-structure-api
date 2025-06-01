package dev.nmarulo.record_structure.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecordStructureReq {
    
    /**
     * Linea del archivo.
     */
    private String line;
    
    /**
     * Identificador (prefijo) de la línea.
     */
    private String lineIdentifier;
    
    private List<RecordField> recordFields;
    
}
