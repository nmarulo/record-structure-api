package dev.nmarulo.record_structure.controllers;

import dev.nmarulo.record_structure.dto.RecordStructureReq;
import dev.nmarulo.record_structure.dto.RecordStructureRes;
import dev.nmarulo.record_structure.services.RecordStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record-structure")
@RequiredArgsConstructor
public class RecordStructureController {
    
    private final RecordStructureService recordStructureService;
    
    @PostMapping
    public ResponseEntity<RecordStructureRes> recordStructure(@RequestBody RecordStructureReq recordStructureReq) {
        return ResponseEntity.ok(this.recordStructureService.recordStructure(recordStructureReq));
    }
    
}
