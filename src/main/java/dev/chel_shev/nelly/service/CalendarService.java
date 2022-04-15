package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.repository.BdayEventRepository;
import dev.chel_shev.nelly.repository.CalendarRepository;
import dev.chel_shev.nelly.repository.WorkoutEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository repository;
    private final BdayEventRepository bdayEventRepository;
    private final WorkoutEventRepository workoutEventRepository;

    public void removeEvent(String name, UserEntity user) {
        List<BdayEventEntity> bdayEntities = bdayEventRepository.findByName(name);
        bdayEntities.forEach(e -> {
            Optional<CalendarEntity> calendarEntity = repository.findByEventAndUser(e, user);
            if (calendarEntity.isPresent()) {
                bdayEventRepository.delete(e);
            }
        });
    }

    public CalendarEntity addEvent(CalendarEntity calendarEntity) {
        return repository.save(calendarEntity);
    }

    public boolean isExist(String name, LocalDateTime date, UserEntity user) {
        BdayEventEntity byNameAndDate = bdayEventRepository.findByNameAndDate(name, date);
        return repository.existsByEventAndUser(byNameAndDate, user);
    }

    public CalendarEntity addEvent(EventEntity event, UserEntity user) {
        return addEvent(new CalendarEntity(event, user));
    }

    public List<WorkoutEventEntity> getUserWorkout(UserEntity user) {
        List<WorkoutEventEntity> workoutEvents = workoutEventRepository.findAll();
        return workoutEvents.stream().map(e -> repository.findByEventAndUser(e, user)).map(e -> {
            CalendarEntity calendarEntity = e.orElse(null);
            if (null != calendarEntity)
                return (WorkoutEventEntity) calendarEntity.getEvent();
            return null;
        }).filter(Objects::nonNull).toList();
    }
}
