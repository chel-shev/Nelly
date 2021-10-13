package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.exception.EasyFinanceException;
import dev.chel_shev.nelly.repository.AccountRepository;
import dev.chel_shev.nelly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public boolean isExist(Long chatId) {
        return repository.existsByChatId(chatId);
    }

    public void save(UserEntity user) {
        repository.saveAndFlush(user);
    }

    public void delete(UserEntity user) {
        repository.delete(user);
    }

    public UserEntity signUp(UserEntity user) {
        if (repository.existsByUserName(user.getUserName()))
            throw new EasyFinanceException(String.format("Username: '%s' already exists!", user.getUserName()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserEntity getUserByName(String name) {
        return repository.findByUserName(name).orElse(null);
    }

    public UserEntity getUserByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    public List<AccountEntity> getAccountList(Long id) {
        return accountRepository.findByUserId(id);
    }
}
