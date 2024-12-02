package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.List;

public interface FastEventService<E extends FastEvent> {

    boolean isExist(String name, FastUserEntity userEntity, LocalDateTime date);

    E getEvent(CallbackQuery callbackQuery);

    E getEvent(String callbackQuery);

    Long save(E e);

    Long save(FastEventEntity e);

    List<FastEventEntity> getAllEvents();

    void deleteByUserSubscription(FastUserSubscriptionEntity userSubscription);

    void deleteByUser(FastUserEntity user);

    void delete(FastEventEntity eventEntity);

    void updateEvent(FastEventEntity eventEntity);

    void initNextEvent(E e);
}