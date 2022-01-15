package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.repository.AccountRepository;
import dev.chel_shev.nelly.type.KeyboardType;
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
                .orElseThrow(() -> new TelegramBotException("Пользователь не найден!", KeyboardType.CANCEL));
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
