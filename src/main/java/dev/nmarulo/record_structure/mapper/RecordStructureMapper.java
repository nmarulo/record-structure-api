package dev.nmarulo.record_structure.mapper;

import dev.nmarulo.record_structure.dto.RecordField;
import dev.nmarulo.record_structure.dto.RecordStructureRes;
import org.springframework.stereotype.Component;

@Component
public class RecordStructureMapper {
    
    public RecordStructureRes.StructuredRecord convertToStructuredRecord(RecordField recordField, Object valueRes) {
        final var structuredRecord = new RecordStructureRes.StructuredRecord();
        
        structuredRecord.setId(recordField.getId());
        structuredRecord.setOrder(recordField.getOrder());
        structuredRecord.setValue(valueRes);
        structuredRecord.setType(recordField.getType());
        
        return structuredRecord;
    }
    
}
