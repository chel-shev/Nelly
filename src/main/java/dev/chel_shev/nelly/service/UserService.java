package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserEntity getUserByChatId(Long chatId) {
        return repository.findByChatId(chatId).orElseThrow(() -> new TelegramBotException("Пользователь не найден!", KeyboardType.CANCEL));
    }

    public boolean isExist(Long chatId) {
        return repository.existsByChatId(chatId);
    }

    public void save(UserEntity user) {
        repository.save(user);
    }

    public void delete(UserEntity user) {
        repository.delete(user);
    }
}
