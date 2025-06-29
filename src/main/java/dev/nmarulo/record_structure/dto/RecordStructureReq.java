package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
