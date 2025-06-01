package dev.nmarulo.record_structure.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecordStructureReq {
    
    /**
     * Linea del archivo.
     */
    private String line;
    
    private List<RecordField> recordFields;
    
}
