package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.repository.event.EventRepository;
import dev.chel_shev.fast.type.SubscriptionStatusType;
import dev.chel_shev.fast.type.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FastUserSubscriptionService {

    private final UserSubscriptionRepository repository;
    private final FastCommandService commandService;
    private final EventRepository eventRepository;
    private final FastEventService eventService;

    public FastUserSubscriptionEntity getSubscription(FastUserEntity user, FastCommandEntity command, SubscriptionType ... types) {
        return repository.findByFastUser_ChatIdAndCommandAndTypeIn(user.getChatId(), command, List.of(types));
    }

    public List<String> getSubscriptions(FastUserEntity user) {
        return repository.findAllByFastUserAndType(user, SubscriptionType.MAIN)
                .stream()
                .map(e -> {
                    if(e.getStatus() == SubscriptionStatusType.ACTIVE)
                        return e.getCommand().getLabel();
                    else
                        return e.getCommand().getLabel() + " \uD83D\uDD15";
                }).toList();
    }

    public List<String> getAvailableSubscriptions(FastUserEntity user) {
        List<String> allByFastUser = repository.findAllByFastUserAndType(user, SubscriptionType.MAIN).stream().map(FastUserSubscriptionEntity::getCommand).map(FastCommandEntity::getLabel).toList();
        List<String> allSubscription = commandService.getAllSubscription().stream().map(FastCommandEntity::getLabel).toList();
        return allSubscription.stream().filter(e -> !allByFastUser.contains(e)).toList();
    }

    public void addSubscription(FastUserEntity user, FastCommandEntity subscription, FastCommandEntity parentCommand, SubscriptionType type, String name) {
        repository.save(new FastUserSubscriptionEntity(user, subscription, parentCommand, type, SubscriptionStatusType.ACTIVE, name));
    }

    public void removeSubscription(FastUserEntity user, FastCommandEntity subscription) {
        FastUserSubscriptionEntity userSubscription = getSubscription(user, subscription, SubscriptionType.MAIN, SubscriptionType.SUB);
        eventService.deleteByUserSubscription(userSubscription);
        repository.delete(userSubscription);
    }

    public void save(FastUserSubscriptionEntity subscription) {
        repository.save(subscription);
    }
}
