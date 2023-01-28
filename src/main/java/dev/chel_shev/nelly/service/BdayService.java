package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.repository.event.BdayEventRepository;
import dev.chel_shev.nelly.repository.user.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.type.EventType.BDAY;

@Service
@RequiredArgsConstructor
public class BdayService {

    private final BdayEventRepository repository;
    private final UserSubscriptionRepository userSubscriptionRepository;

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

    public UserSubscriptionEntity getSubscription(UserEntity user) {
        return userSubscriptionRepository.findByUserAndSubscriptionEventType(user, BDAY);
    }

//    @Override
//    public List<CalendarEntity> getCalendarEntities(BdayEntity entity, UserEntity user) {
//        return new ArrayList<>() {{
//            add(new CalendarEntity(entity.getDate().withHour(10).withMinute(0), entity, user));
//        }};
//    }
}
