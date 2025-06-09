package dev.nmarulo.record_structure.controllers;

import dev.nmarulo.record_structure.dto.*;
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
    
    @PostMapping("/file")
    public ResponseEntity<RecordStructureFileRes> recordStructureFromFile(@RequestBody RecordStructureFileReq recordStructureReq) {
        return ResponseEntity.ok(this.recordStructureService.recordStructureFromFile(recordStructureReq));
    }
    
    @PostMapping("/generate-csv")
    public ResponseEntity<GenerateCsvRecordStructureRes> generateCsvFromFile(@RequestBody GenerateCsvRecordStructureReq request) {
        return ResponseEntity.ok(this.recordStructureService.generateCsvFromFile(request));
    }
    
}
