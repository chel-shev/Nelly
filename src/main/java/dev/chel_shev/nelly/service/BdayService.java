package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.repository.BdayEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BdayService {

    private final BdayEventRepository repository;

    public boolean isExist(String name, LocalDateTime date, UserEntity user) {
        return repository.existsByNameAndDate(name, date);
    }

    public boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public BdayEventEntity save(BdayEventEntity entity) {
        return repository.save(entity);
    }

    public void updateEvent(BdayEventEntity entity) {
        LocalDateTime eventDateTime = entity.getEventDateTime();
        entity.setEventDateTime(eventDateTime.plusYears(1));
        save(entity);
    }

//    @Override
//    public List<CalendarEntity> getCalendarEntities(BdayEntity entity, UserEntity user) {
//        return new ArrayList<>() {{
//            add(new CalendarEntity(entity.getDate().withHour(10).withMinute(0), entity, user));
//        }};
//    }
}
