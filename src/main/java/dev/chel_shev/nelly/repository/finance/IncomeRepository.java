package dev.chel_shev.nelly.repository.finance;

import dev.chel_shev.nelly.entity.event.finance.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
}
