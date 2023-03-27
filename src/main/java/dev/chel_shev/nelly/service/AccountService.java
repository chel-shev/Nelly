package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.exception.NellyException;
import dev.chel_shev.nelly.repository.finance.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accR;

    public AccountEntity getAccountByChatId(Long chatId) {
        return accR.findByUserChatId(chatId)
                .stream()
                .filter(AccountEntity::isMain)
                .findFirst()
                .orElseThrow(() -> new NellyException("Пользователь не найден!"));
    }

    public Collection<AccountEntity> getAccountListByChatId(Long chatId) {
        return accR.findByUserChatId(chatId);
    }

    public List<AccountEntity> getAccountListByUserId(Long chatId) {
        return accR.findByUserId(chatId);
    }

    public AccountEntity save(AccountEntity account) {
        return accR.save(account);
    }
}
