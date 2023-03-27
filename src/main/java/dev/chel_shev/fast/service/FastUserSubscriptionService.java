package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.type.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FastUserSubscriptionService {

    private final UserSubscriptionRepository repository;
    private final FastCommandService commandService;

    public FastUserSubscriptionEntity getSubscription(FastUserEntity user, FastCommandEntity command) {
        return repository.findByFastUserAndCommand(user, command);
    }

    public List<String> getSubscriptions(FastUserEntity user) {
        return repository.findAllByFastUser(user).stream().map(FastUserSubscriptionEntity::getCommand).map(FastCommandEntity::getLabel).toList();
    }

    public List<String> getAvailableSubscriptions(FastUserEntity user) {
        List<String> allByFastUser = repository.findAllByFastUser(user).stream().map(FastUserSubscriptionEntity::getCommand).map(FastCommandEntity::getLabel).toList();
        List<String> allSubscription = commandService.getAllSubscription().stream().map(FastCommandEntity::getLabel).toList();
        return allSubscription.stream().filter(e -> !allByFastUser.contains(e)).toList();
    }

    public void addSubscription(FastUserEntity user, FastCommandEntity subscription) {
        repository.save(new FastUserSubscriptionEntity(user, subscription, SubscriptionType.MAIN, subscription.getName()));
    }

    public void removeSubscription(FastUserEntity user, FastCommandEntity subscription) {
        FastUserSubscriptionEntity userSubscription = getSubscription(user, subscription);
        repository.delete(userSubscription);
    }
}
