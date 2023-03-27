package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.event.FastEvent;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;

public interface FastEventService<E extends FastEvent>  {

    boolean isExist(String name, FastUserEntity userEntity, LocalDateTime date);
    E getEvent(CallbackQuery callbackQuery);

    Long save(E e);
    Long save(FastEventEntity e);
    void deleteByUser(FastUserEntity userEntity);

    void updateEvent(FastEventEntity eventEntity);
}