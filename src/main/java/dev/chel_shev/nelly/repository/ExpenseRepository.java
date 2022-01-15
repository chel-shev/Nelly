package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.finance.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    Iterable<ExpenseEntity> findAllByConfirmed(Boolean confirmed);
}
