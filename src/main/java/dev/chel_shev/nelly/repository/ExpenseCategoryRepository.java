package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.ExpenseCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    Optional<ExpenseCategoryEntity> getByName(String name);
}
