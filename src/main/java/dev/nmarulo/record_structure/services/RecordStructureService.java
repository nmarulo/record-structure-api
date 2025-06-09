package dev.nmarulo.record_structure.services;

import dev.nmarulo.record_structure.dto.*;
import dev.nmarulo.record_structure.exception.BadRequestException;
import dev.nmarulo.record_structure.exception.InternalServerErrorException;
import dev.nmarulo.record_structure.mapper.RecordStructureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordStructureService {
    
    private final RecordStructureMapper recordStructureMapper;
    
    public RecordStructureRes recordStructure(RecordStructureReq recordStructureReq) {
        final var startPosition = new AtomicInteger(0);
        final var line = recordStructureReq.getLine();
        
        if (line == null || line.isBlank()) {
            throw new BadRequestException("La linea no puede estar vaciá");
        }
        
        final var lineIdentifier = recordStructureReq.getLineIdentifier();
        
        if (lineIdentifier == null || lineIdentifier.isBlank()) {
            throw new BadRequestException("El identificador de la linea no puede estar vaciá");
        }
        
        if (!line.startsWith(lineIdentifier)) {
            return new RecordStructureRes();
        }
        
        final var structuredRecords = recordStructureReq.getRecordFields()
                                                        .stream()
                                                        .sorted(Comparator.comparing(RecordField::getOrder))
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
        
        return new RecordStructureRes(lineIdentifier, structuredRecords);
    }
    
    public RecordStructureFileRes recordStructureFromFile(RecordStructureFileReq recordStructureFileReq) {
        final var filePath = recordStructureFileReq.getFilePath();
        final var path = Path.of(filePath);
        final var file = path.toFile();
        
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new BadRequestException("El archivo no existe o no se puede leer");
        }
        
        final var recordStructures = new LinkedList<RecordStructureRes>();
        
        try (final var bufferedReader = Files.newBufferedReader(path)) {
            var line = bufferedReader.readLine();
            
            while (line != null && !line.isBlank()) {
                final var recordStructureReq = this.recordStructureMapper.convertToRecordStructureReq(line,
                                                                                                      recordStructureFileReq);
                line = bufferedReader.readLine();
                
                final var structuredRecords = recordStructure(recordStructureReq);
                
                if (structuredRecords.getLineIdentifier() == null) {
                    continue;
                }
                
                recordStructures.add(structuredRecords);
            }
        } catch (IOException e) {
            throw new BadRequestException("Error al leer el archivo", e);
        }
        
        return new RecordStructureFileRes(recordStructures);
    }
    
    private Object getValueFormat(RecordField recordField, String value) {
        final var type = recordField.getType();
        final var format = recordField.getFormat();
        
        return switch (type) {
            case DATE -> getDateFormat(format, value);
            case DECIMAL -> getDecimalFormat(format, value);
            case INTEGER -> Integer.valueOf(value);
            case STRING -> value.trim();
        };
    }
    
    private Double getDecimalFormat(FieldFormat fieldFormat, String value) {
        return switch (fieldFormat) {
            case DEFAULT -> Integer.parseInt(value) / 100.0;
            case FIXED_POINT_NUMBERS -> Double.valueOf(value);
            default -> throw new IllegalStateException("Unexpected value: " + fieldFormat.getFormat());
        };
    }
    
    private Object getDateFormat(FieldFormat fieldFormat, String value) {
        return switch (fieldFormat) {
            case DDMMYYYY -> LocalDate.parse(value, DateTimeFormatter.ofPattern(fieldFormat.getFormat()));
            case HHMMSS -> LocalTime.parse(value, DateTimeFormatter.ofPattern(fieldFormat.getFormat()));
            default -> throw new IllegalStateException("Unexpected value: " + fieldFormat.getFormat());
        };
    }
    
    public GenerateCsvRecordStructureRes generateCsvFromFile(GenerateCsvRecordStructureReq request) {
        final var recordStructureFileRes = recordStructureFromFile(request);
        final var recordStructures = recordStructureFileRes.getRecordStructures();
        
        if (recordStructures.isEmpty()) {
            throw new BadRequestException("No se encontraron registros para generar el CSV");
        }
        
        final var filePath = request.getFilePath();
        final var csvFilePath = filePath.substring(0, filePath.lastIndexOf('.')) + ".csv";
        final var csvPath = Path.of(csvFilePath);
        
        try (final var writer = Files.newBufferedWriter(csvPath)) {
            writeHeader(writer, recordStructures.getFirst());
            
            for (final var recordStructure : recordStructures) {
                writeRecord(writer, recordStructure);
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("Error al generar el archivo CSV", e);
        }
        
        return new GenerateCsvRecordStructureRes(csvFilePath);
    }
    
    private void writeHeader(BufferedWriter writer, RecordStructureRes recordStructure) throws IOException {
        final var columnNames = recordStructure.getStructuredRecords()
                                               .stream()
                                               .sorted(Comparator.comparing(RecordStructureRes.StructuredRecord::getOrder))
                                               .map(RecordStructureRes.StructuredRecord::getColumnName)
                                               .collect(Collectors.joining(";"));
        
        writer.write(columnNames);
        writer.newLine();
    }
    
    private void writeRecord(BufferedWriter writer, RecordStructureRes recordStructure) throws IOException {
        final var values = recordStructure.getStructuredRecords()
                                          .stream()
                                          .sorted(Comparator.comparing(RecordStructureRes.StructuredRecord::getOrder))
                                          .map(RecordStructureRes.StructuredRecord::getValue)
                                          .map(String::valueOf)
                                          .collect(Collectors.joining(";"));
        
        writer.write(values);
        writer.newLine();
    }
    
}
