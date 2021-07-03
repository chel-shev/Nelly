package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.BdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.time.LocalDate;

public interface BdayRepository extends JpaRepository<BdayEntity, Long> {

    boolean existsByNameAndDate(String name, LocalDate date);

    boolean existsByName(String name);

    @Modifying
    @Transactional
    void deleteAllByName(String name);
}
