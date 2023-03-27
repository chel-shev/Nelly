package dev.chel_shev.nelly.repository.finance;


import dev.chel_shev.nelly.entity.finance.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Collection<LoanEntity> findAllByAccountUser(String chatId);
}
