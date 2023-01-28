package dev.chel_shev.nelly.repository.finance;

import dev.chel_shev.nelly.entity.event.finance.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Collection<AccountEntity> findByUserChatId(Long chatId);

    List<AccountEntity> findByUserId(Long Id);
}
