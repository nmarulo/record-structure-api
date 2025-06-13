package dev.nmarulo.record_structure.controllers;

import dev.nmarulo.record_structure.dto.FindAllTypeRecordRes;
import dev.nmarulo.record_structure.dto.SaveTypeRecordReq;
import dev.nmarulo.record_structure.dto.TypeRecordRes;
import dev.nmarulo.record_structure.dto.UpdateTypeRecordReq;
import dev.nmarulo.record_structure.services.TypeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type-records")
@RequiredArgsConstructor
public class TypeRecordController {
    
    private final TypeRecordService typeRecordService;
    
    @GetMapping
    public ResponseEntity<FindAllTypeRecordRes> findAll() {
        return ResponseEntity.ok(typeRecordService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TypeRecordRes> findById(@PathVariable Long id) {
        return ResponseEntity.ok(typeRecordService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<TypeRecordRes> save(@RequestBody SaveTypeRecordReq request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(typeRecordService.save(request));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TypeRecordRes> update(@PathVariable Long id, @RequestBody UpdateTypeRecordReq request) {
        return ResponseEntity.ok(typeRecordService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        typeRecordService.delete(id);
        
        return ResponseEntity.noContent()
                             .build();
    }
    
}
