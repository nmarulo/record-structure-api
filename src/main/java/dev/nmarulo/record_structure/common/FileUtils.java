package dev.nmarulo.record_structure.common;

import lombok.RequiredArgsConstructor;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileUtils {
    
    private final EncodingDetector encodingDetector;
    
    public Optional<Charset> charsetDetect(Path filePath) {
        final Metadata metadata = new Metadata();
        
        try (final TikaInputStream tikaInputStream = TikaInputStream.get(filePath, metadata)) {
            final Charset charset = this.encodingDetector.detect(tikaInputStream, metadata);
            
            return Optional.ofNullable(charset);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
    
}
