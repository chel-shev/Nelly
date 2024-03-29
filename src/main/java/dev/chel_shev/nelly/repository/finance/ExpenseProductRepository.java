package dev.chel_shev.nelly.repository.finance;

import dev.chel_shev.nelly.entity.finance.ExpenseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseProductRepository extends JpaRepository<ExpenseProductEntity, Long> {

    Optional<ExpenseProductEntity> getByNameAndExpenseCategoryName(String expenseProductName, String expenseCategoryName);

    Optional<ExpenseProductEntity> findByName(String name);
}
