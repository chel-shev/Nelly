package dev.chel_shev.nelly.repository;


import dev.chel_shev.nelly.entity.finance.LoanEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Collection<LoanEntity> findAllByAccountUser(UserEntity user);
}
