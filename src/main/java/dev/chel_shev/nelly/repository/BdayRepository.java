package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.BdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BdayRepository extends JpaRepository<BdayEntity, Long> {

    boolean existsByNameAndDate(String name, LocalDate date);
}
