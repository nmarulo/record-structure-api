package dev.nmarulo.record_structure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStructureFileRes {
    
    private List<RecordStructureRes> recordStructures;
    
}
