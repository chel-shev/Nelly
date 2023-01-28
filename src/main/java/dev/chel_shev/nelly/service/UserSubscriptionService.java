package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.repository.user.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSubscriptionService {

    private UserSubscriptionRepository repository;

    public UserSubscriptionEntity getUserByChatId(Long chatId) {
        return repository.findByUserChatId(chatId);
    }
}
