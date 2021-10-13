package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.repository.BdayRepository;
import dev.chel_shev.nelly.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository repository;
    private final BdayRepository bdayRepository;

    public void removeEvent(String name, UserEntity user) {
        List<BdayEntity> bdayEntities = bdayRepository.findByName(name);
        bdayEntities.forEach(e -> {
            Optional<CalendarEntity> calendarEntity = repository.findByEventAndUser(e, user);
            calendarEntity.ifPresent(repository::delete);
        });
    }

    public void addEvents(List<CalendarEntity> calendarEntities) {
        calendarEntities.forEach(repository::save);
    }
}
