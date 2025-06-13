package dev.nmarulo.record_structure.mapper;

import dev.nmarulo.record_structure.dto.FindAllTypeRecordRes;
import dev.nmarulo.record_structure.dto.SaveTypeRecordReq;
import dev.nmarulo.record_structure.dto.TypeRecordRes;
import dev.nmarulo.record_structure.dto.UpdateTypeRecordReq;
import dev.nmarulo.record_structure.entity.TypeRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeRecordMapper {
    
    public TypeRecordRes convertToTypeRecordReq(TypeRecord typeRecord) {
        final var response = new TypeRecordRes();
        
        response.setId(typeRecord.getId());
        response.setName(typeRecord.getName());
        response.setLineIdentifier(typeRecord.getLineIdentifier());
        response.setLengths(typeRecord.getLengths());
        response.setColumns(typeRecord.getColumns());
        
        return response;
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
