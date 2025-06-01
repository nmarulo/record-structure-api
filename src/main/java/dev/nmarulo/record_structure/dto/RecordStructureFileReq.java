package dev.nmarulo.record_structure.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecordStructureFileReq {
    
    private String filePath;
    
    /**
     * Identificador (prefijo) de la línea.
     */
    private String lineIdentifier;
    
    private List<RecordField> recordFields;
    
}
