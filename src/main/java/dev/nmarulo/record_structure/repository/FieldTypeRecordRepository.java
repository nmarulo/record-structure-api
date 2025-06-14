package dev.nmarulo.record_structure.repository;

import dev.nmarulo.record_structure.entity.FieldTypeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeRecordRepository extends JpaRepository<FieldTypeRecord, Long> {
    
    void deleteAllByTypeRecord_Id(Long id);
    
}
