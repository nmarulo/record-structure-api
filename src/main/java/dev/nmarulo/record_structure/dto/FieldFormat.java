package dev.nmarulo.record_structure.dto;

import lombok.Getter;

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
