package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.finance.StatisticCategoryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticCategoryRepository extends JpaRepository<StatisticCategoryEntity, Long> {

    List<StatisticCategoryEntity> findAll(Sort by);
}
