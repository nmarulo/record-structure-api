package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAllTypeRecordRes {
    
    private List<TypeRecordRes> typeRecords;
    
}
