package dev.nmarulo.record_structure.services;

import dev.nmarulo.record_structure.dto.FindAllTypeRecordRes;
import dev.nmarulo.record_structure.dto.SaveTypeRecordReq;
import dev.nmarulo.record_structure.dto.TypeRecordRes;
import dev.nmarulo.record_structure.dto.UpdateTypeRecordReq;
import dev.nmarulo.record_structure.entity.FieldTypeRecord;
import dev.nmarulo.record_structure.entity.TypeRecord;
import dev.nmarulo.record_structure.exception.NotFoundException;
import dev.nmarulo.record_structure.mapper.TypeRecordMapper;
import dev.nmarulo.record_structure.repository.FieldTypeRecordRepository;
import dev.nmarulo.record_structure.repository.TypeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TypeRecordService {
    
    private final TypeRecordRepository typeRecordRepository;
    
    private final FieldTypeRecordRepository fieldTypeRecordRepository;
    
    private final TypeRecordMapper typeRecordMapper;
    
    @Transactional(readOnly = true)
    public FindAllTypeRecordRes findAll() {
        final var typeRecordList = this.typeRecordRepository.findAll();
        
        return this.typeRecordMapper.convertToFindAllTypeRecordRes(typeRecordList);
    }
    
    @Transactional(readOnly = true)
    public TypeRecordRes findById(Long id) {
        return typeRecordRepository.findById(id)
                                   .map(this.typeRecordMapper::convertToTypeRecordReq)
                                   .orElseThrow(() -> new NotFoundException("TypeRecord not found with id: " + id));
    }
    
    @Transactional
    public TypeRecordRes save(SaveTypeRecordReq request) {
        final var typeRecord = typeRecordMapper.convertToTypeRecord(request);
        
        addFieldTypeRecords(typeRecord, request.getColumns(), request.getLengths());
        
        final var savedTypeRecord = typeRecordRepository.save(typeRecord);
        
        return typeRecordMapper.convertToTypeRecordReq(savedTypeRecord);
    }
    
    @Transactional
    public TypeRecordRes update(Long id, UpdateTypeRecordReq request) {
        final var typeRecord = this.typeRecordRepository.findById(id)
                                                        .orElseThrow(() -> new NotFoundException(
                                                            "TypeRecord not found with id: " + id));
        typeRecord.getFieldTypeRecords()
                  .clear();
        this.fieldTypeRecordRepository.deleteAllByTypeRecord_Id(id);
        
        this.typeRecordMapper.mapperToTypeRecord(request, typeRecord);
        addFieldTypeRecords(typeRecord, request.getColumns(), request.getLengths());
        
        final var updatedTypeRecord = typeRecordRepository.save(typeRecord);
        
        return typeRecordMapper.convertToTypeRecordReq(updatedTypeRecord);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!typeRecordRepository.existsById(id)) {
            throw new NotFoundException("TypeRecord not found with id: " + id);
        }
        
        typeRecordRepository.deleteById(id);
    }
    
    private void addFieldTypeRecords(TypeRecord typeRecord, String columns, String lengths) {
        final var columnArray = split(columns);
        final var lengthArray = split(lengths);
        
        for (int i = 0; i < lengthArray.length; i++) {
            final var fieldTypeRecord = new FieldTypeRecord();
            final var columnName = getColumnName(columnArray, i);
            
            fieldTypeRecord.setColumnName(columnName);
            fieldTypeRecord.setOrder(i);
            fieldTypeRecord.setLength(parseInt(lengthArray[i]));
            fieldTypeRecord.setType(FieldTypeRecord.FieldType.STRING);
            fieldTypeRecord.setFormat(FieldTypeRecord.FieldFormat.DEFAULT);
            
            typeRecord.addFieldTypeRecord(fieldTypeRecord);
        }
    }
    
    private String getColumnName(String[] columnArray, int index) {
        if (columnArray.length < index) {
            return "COLUMN_" + index;
        }
        
        return columnArray[index];
    }
    
    private String[] split(String value) {
        if (!StringUtils.hasText(value)) {
            return new String[0];
        }
        
        if (value.contains(",")) {
            return value.split(",");
        }
        
        if (value.contains("\n")) {
            return value.split("\n");
        }
        
        return new String[] {value};
    }
    
    public int parseInt(String value) {
        if (!StringUtils.hasText(value)) {
            return 0;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
}
