package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFieldTypeRecordReq {
    
    private Long id;
    
    private String columnName;
    
    private Integer order;
    
    private Integer length;
    
    private String type;
    
    private String format;
    
}
