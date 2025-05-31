package dev.nmarulo.record_structure.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class RecordStructureReq {
    
    /**
     * Linea del archivo.
     */
    private String line;
    
    private List<RecordField> recordFields;
    
    /**
     * Detalles de un campo del registro.
     */
    @Data
    public static class RecordField {
        
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
    
    @Getter
    public enum FieldFormat {
        DDMMYYYY("ddMMyyyy"),
        HHMMSS("HHmmss"),
        FIXED_POINT_NUMBERS("0.00"),
        DEFAULT("");
        
        private final String format;
        
        FieldFormat(String format) {
            this.format = format;
        }
        
    }
    
}
