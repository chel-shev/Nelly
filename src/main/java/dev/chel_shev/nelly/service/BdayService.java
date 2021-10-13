package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.repository.BdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BdayService implements EventService<BdayEntity> {

    private final BdayRepository repository;

    public boolean isExist(String name, LocalDateTime date, UserEntity user) {
        return repository.existsByNameAndDate(name, date);
    }

    public boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public BdayEntity save(BdayEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<CalendarEntity> getCalendarEntities(BdayEntity entity, UserEntity user) {
        return new ArrayList<>() {{
            add(new CalendarEntity(entity.getDate().withHour(10).withMinute(0), entity, EventType.EVERY_YEAR, user));
        }};
    }
}
