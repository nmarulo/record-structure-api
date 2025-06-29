package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeRecordRes {
    
    private Long id;
    
    private String name;
    
    private String lineIdentifier;
    
    private String lengths;
    
    private String columns;
    
    private List<FieldTypeRecordRes> fieldTypeRecords;
    
}
