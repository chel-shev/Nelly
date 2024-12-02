package dev.chel_shev.mind.repository;

import dev.chel_shev.mind.entity.MiRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<MiRecordEntity, Long> {
}
