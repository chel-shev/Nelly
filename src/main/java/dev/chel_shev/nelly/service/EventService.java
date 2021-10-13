package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.EventEntity;
import dev.chel_shev.nelly.entity.UserEntity;

import java.util.List;

public interface EventService<E extends EventEntity> {

    List<CalendarEntity> getCalendarEntities(E entity, UserEntity user);
}