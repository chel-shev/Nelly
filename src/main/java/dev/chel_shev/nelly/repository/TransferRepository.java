package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.finance.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

}
