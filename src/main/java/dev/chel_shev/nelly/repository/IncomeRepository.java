package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
}
