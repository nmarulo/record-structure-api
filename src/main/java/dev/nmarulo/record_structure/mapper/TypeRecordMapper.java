package dev.nmarulo.record_structure.mapper;

import dev.nmarulo.record_structure.dto.*;
import dev.nmarulo.record_structure.entity.FieldTypeRecord;
import dev.nmarulo.record_structure.entity.TypeRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeRecordMapper {
    
    public TypeRecordRes convertToTypeRecordReq(TypeRecord typeRecord) {
        final var response = new TypeRecordRes();
        final var fieldTypeRecordRes = typeRecord.getFieldTypeRecords()
                                                 .stream()
                                                 .map(this::convertToFieldTypeRecord)
                                                 .toList();
        
        response.setId(typeRecord.getId());
        response.setName(typeRecord.getName());
        response.setLineIdentifier(typeRecord.getLineIdentifier());
        response.setLengths(typeRecord.getLengths());
        response.setColumns(typeRecord.getColumns());
        response.setFieldTypeRecords(fieldTypeRecordRes);
        
        return response;
    }
    
    public FieldTypeRecordRes convertToFieldTypeRecord(FieldTypeRecord fieldTypeRecord) {
        final var fieldTypeRecordRes = new FieldTypeRecordRes();
        
        fieldTypeRecordRes.setId(fieldTypeRecord.getId());
        fieldTypeRecordRes.setColumnName(fieldTypeRecord.getColumnName());
        fieldTypeRecordRes.setOrder(fieldTypeRecord.getOrder());
        fieldTypeRecordRes.setLength(fieldTypeRecord.getLength());
        fieldTypeRecordRes.setType(fieldTypeRecord.getType()
                                                  .toString());
        fieldTypeRecordRes.setFormat(fieldTypeRecord.getFormat()
                                                    .toString());
        
        return fieldTypeRecordRes;
    }
    
    public TypeRecord convertToTypeRecord(SaveTypeRecordReq request) {
        final var typeRecord = new TypeRecord();
        
        typeRecord.setName(request.getName());
        typeRecord.setLineIdentifier(request.getLineIdentifier());
        typeRecord.setLengths(request.getLengths());
        typeRecord.setColumns(request.getColumns());
        
        return typeRecord;
    }
    
    public FindAllTypeRecordRes convertToFindAllTypeRecordRes(List<TypeRecord> typeRecordList) {
        final var typeRecordResList = typeRecordList.stream()
                                                    .map(this::convertToTypeRecordReq)
                                                    .collect(Collectors.toList());
        
        return new FindAllTypeRecordRes(typeRecordResList);
    }
    
    public void mapperToTypeRecord(UpdateTypeRecordReq request, TypeRecord typeRecord) {
        typeRecord.setName(request.getName());
        typeRecord.setLineIdentifier(request.getLineIdentifier());
        typeRecord.setLengths(request.getLengths());
        typeRecord.setColumns(request.getColumns());
    }
    
}
