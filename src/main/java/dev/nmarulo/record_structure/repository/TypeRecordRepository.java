package dev.nmarulo.record_structure.repository;

import dev.nmarulo.record_structure.entity.TypeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRecordRepository extends JpaRepository<TypeRecord, Long> {}
