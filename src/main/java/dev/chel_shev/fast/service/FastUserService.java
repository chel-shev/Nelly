package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FastUserService {

    private final FastUserRepository userRepository;
    private final FastAnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;

    public Optional<FastUserEntity> getUserByChatId(String chatId) {
        return userRepository.findByChatId(chatId);
//                .orElseThrow(() -> new FastBotException(answerService.generateAnswer(FastBotCommandLevel.FIRST, unknownUserConfig)));
    }

    public boolean isExist(String chatId) {
        return userRepository.existsByChatId(chatId);
    }

    public void save(FastUserEntity user) {
        userRepository.save(user);
    }

    public void delete(FastUserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    public FastUserEntity getFastUserByName(String username) {
        return userRepository.findByUserName(username);
    }
}
