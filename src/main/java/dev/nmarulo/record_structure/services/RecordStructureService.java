package dev.nmarulo.record_structure.services;

import dev.nmarulo.record_structure.dto.RecordStructureReq;
import dev.nmarulo.record_structure.dto.RecordStructureRes;
import dev.nmarulo.record_structure.mapper.RecordStructureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RecordStructureService {
    
    private final RecordStructureMapper recordStructureMapper;
    
    public RecordStructureRes recordStructure(RecordStructureReq recordStructureReq) {
        final var startPosition = new AtomicInteger(0);
        final var line = recordStructureReq.getLine();
        final var structuredRecords = recordStructureReq.getRecordFields()
                                                        .stream()
                                                        .sorted(Comparator.comparing(RecordStructureReq.RecordField::getOrder))
                                                        .map(recordField -> {
                                                            final var length = recordField.getLength();
                                                            final var value = line.substring(startPosition.get(),
                                                                                             startPosition.addAndGet(
                                                                                                 length));
                                                            final var valueRes = getValueFormat(recordField, value);
                                                            
                                                            return this.recordStructureMapper.convertToStructuredRecord(
                                                                recordField,
                                                                valueRes);
                                                        })
                                                        .toList();
        
        return new RecordStructureRes(structuredRecords);
    }
    
    private Object getValueFormat(RecordStructureReq.RecordField recordField, String value) {
        final var type = recordField.getType();
        final var format = recordField.getFormat();
        
        return switch (type) {
            case DATE -> getDateFormat(format, value);
            case DECIMAL -> getDecimalFormat(format, value);
            case INTEGER -> Integer.valueOf(value);
            case STRING -> value.trim();
        };
    }
    
    private Double getDecimalFormat(RecordStructureReq.FieldFormat fieldFormat, String value) {
        return switch (fieldFormat) {
            case DEFAULT -> Integer.parseInt(value) / 100.0;
            case FIXED_POINT_NUMBERS -> Double.valueOf(value);
            default -> throw new IllegalStateException("Unexpected value: " + fieldFormat.getFormat());
        };
    }
    
    private Object getDateFormat(RecordStructureReq.FieldFormat fieldFormat, String value) {
        return switch (fieldFormat) {
            case DDMMYYYY -> LocalDate.parse(value, DateTimeFormatter.ofPattern(fieldFormat.getFormat()));
            case HHMMSS -> LocalTime.parse(value, DateTimeFormatter.ofPattern(fieldFormat.getFormat()));
            default -> throw new IllegalStateException("Unexpected value: " + fieldFormat.getFormat());
        };
    }
    
}
