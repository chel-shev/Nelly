package dev.chel_shev.nelly.repository.finance;

import dev.chel_shev.nelly.entity.event.finance.IncomeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {
}
