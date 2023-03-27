package dev.chel_shev.nelly.repository.user;

import dev.chel_shev.nelly.entity.finance.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientHistoryRepository extends JpaRepository<AccountHistoryEntity, Long> {

}
