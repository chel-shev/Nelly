package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.repository.event.BdayEventRepository;
import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FastBdayEventService extends FastEventServiceImpl<BdayEvent> {

    private final BdayEventRepository repository;

    public FastBdayEventService(FastEventRepository repository, FastUserService userService, FastAnswerService answerService, UnknownUserConfig unknownUserConfig, UserSubscriptionRepository userSubscriptionRepository, BdayEventRepository repository1) {
        super(repository, userService, answerService, unknownUserConfig, userSubscriptionRepository);
        this.repository = repository1;
    }

    public boolean isExist(String name, LocalDateTime date, UserEntity user) {
        return repository.existsByNameAndDate(name, date);
    }

    public boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public void removeEvent(String name, FastUserEntity userEntity) {
        FastBdayEventEntity byNameAndUserFastUser = repository.findByNameAndUser_FastUser(name, userEntity);
        repository.delete(byNameAndUserFastUser);
    }

    @Override
    public boolean isExist(String name, FastUserEntity userEntity, LocalDateTime date) {
        return false;
    }

    @Override
    public BdayEvent getEvent(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public BdayEvent getEvent(String callbackQuery) {
        return null;
    }

    @Override
    public Long save(BdayEvent bdayEvent) {
        return null;
    }

    @Override
    public Long save(FastEventEntity e) {
        return null;
    }

    @Override
    public List<FastEventEntity> getAllEvents() {
        return null;
    }

    @Override
    public void delete(FastEventEntity eventEntity) {

    }

    @Override
    public void updateEvent(FastEventEntity entity) {
        LocalDateTime eventDateTime = entity.getDateTime();
        entity.setDateTime(eventDateTime.plusYears(1));
        repository.save((FastBdayEventEntity) entity);
    }

    @Override
    public void initNextEvent(BdayEvent bdayEvent) {

    }

    public List<String> getAllBdayName(FastUserEntity userEntity) {
        return repository.findAllByUser_FastUser(userEntity).stream().map(FastBdayEventEntity::getName).toList();
    }


//    @Override
//    public List<CalendarEntity> getCalendarEntities(BdayEntity entity, UserEntity user) {
//        return new ArrayList<>() {{
//            add(new CalendarEntity(entity.getDate().withHour(10).withMinute(0), entity, user));
//        }};
//    }
}
