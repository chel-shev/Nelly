package dev.chel_shev.nelly.repository.finance;

import dev.chel_shev.nelly.entity.event.finance.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

}
