package dev.nmarulo.record_structure.services;

import dev.nmarulo.record_structure.dto.FindAllTypeRecordRes;
import dev.nmarulo.record_structure.dto.SaveTypeRecordReq;
import dev.nmarulo.record_structure.dto.TypeRecordRes;
import dev.nmarulo.record_structure.dto.UpdateTypeRecordReq;
import dev.nmarulo.record_structure.exception.NotFoundException;
import dev.nmarulo.record_structure.mapper.TypeRecordMapper;
import dev.nmarulo.record_structure.repository.TypeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TypeRecordService {
    
    private final TypeRecordRepository typeRecordRepository;
    
    private final TypeRecordMapper typeRecordMapper;
    
    @Transactional(readOnly = true)
    public FindAllTypeRecordRes findAll() {
        final var typeRecordList = this.typeRecordRepository.findAll();
        
        return this.typeRecordMapper.convertToFindAllTypeRecordRes(typeRecordList);
    }
    
    @Transactional(readOnly = true)
    public TypeRecordRes findById(Long id) {
        return typeRecordRepository.findById(id)
                                   .map(typeRecordMapper::convertToTypeRecordReq)
                                   .orElseThrow(() -> new NotFoundException("TypeRecord not found with id: " + id));
    }
    
    @Transactional
    public TypeRecordRes save(SaveTypeRecordReq request) {
        final var typeRecord = typeRecordMapper.convertToTypeRecord(request);
        final var savedTypeRecord = typeRecordRepository.save(typeRecord);
        
        return typeRecordMapper.convertToTypeRecordReq(savedTypeRecord);
    }
    
    @Transactional
    public TypeRecordRes update(Long id, UpdateTypeRecordReq request) {
        final var typeRecord = this.typeRecordRepository.findById(id)
                                                        .orElseThrow(() -> new NotFoundException(
                                                            "TypeRecord not found with id: " + id));
        
        this.typeRecordMapper.mapperToTypeRecord(request, typeRecord);
        
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
    
}
