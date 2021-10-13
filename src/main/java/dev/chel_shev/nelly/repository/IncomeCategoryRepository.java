package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.IncomeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {
}
