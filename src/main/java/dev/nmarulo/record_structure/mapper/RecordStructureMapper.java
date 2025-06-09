package dev.nmarulo.record_structure.mapper;

import dev.nmarulo.record_structure.dto.RecordField;
import dev.nmarulo.record_structure.dto.RecordStructureFileReq;
import dev.nmarulo.record_structure.dto.RecordStructureReq;
import dev.nmarulo.record_structure.dto.RecordStructureRes;
import org.springframework.stereotype.Component;

@Component
public class RecordStructureMapper {
    
    public RecordStructureRes.StructuredRecord convertToStructuredRecord(RecordField recordField, Object valueRes) {
        final var structuredRecord = new RecordStructureRes.StructuredRecord();
        
        structuredRecord.setOrder(recordField.getOrder());
        structuredRecord.setValue(valueRes);
        structuredRecord.setType(recordField.getType());
        structuredRecord.setColumnName(recordField.getColumnName());
        
        return structuredRecord;
    }
    
    public RecordStructureReq convertToRecordStructureReq(String line, RecordStructureFileReq recordStructureFileReq) {
        return new RecordStructureReq(line,
                                      recordStructureFileReq.getLineIdentifier(),
                                      recordStructureFileReq.getRecordFields());
    }
    
}
