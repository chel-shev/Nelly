package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventFactory;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownUserConfig;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.repository.BdayEventRepository;
import dev.chel_shev.nelly.repository.EventRepository;
import dev.chel_shev.nelly.repository.WorkoutEventRepository;
import dev.chel_shev.nelly.type.CommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class EventService<E extends Event> {

    private final EventRepository repository;
    private final UserService userService;
    private final AnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;
    private final EventFactory<E> eventFactory;

    private final BdayEventRepository bdayEventRepository;
    private final WorkoutEventRepository workoutEventRepository;

    public void removeEvent(String name, UserEntity user) {
        List<BdayEventEntity> bdayEntities = bdayEventRepository.findByNameAndUser(name, user);
        repository.deleteAll(bdayEntities);
    }

    public boolean isExist(String name, LocalDateTime date, UserEntity user) {
        return bdayEventRepository.existsByNameAndDateAndUser(name, date, user);
    }

    public List<WorkoutEventEntity> getUserWorkouts(UserEntity user) {
        return workoutEventRepository.findByUser(user).stream().filter(Objects::nonNull).toList();
    }

    public void deleteByUser(UserEntity user) {
        List<EventEntity> allByUser = repository.findAllByUser(user);
        repository.deleteAll(allByUser);
    }

    public E getInquiryByCallbackQuery(CallbackQuery callbackQuery) {
        var user = userService.getUserByChatId(callbackQuery.getMessage().getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
        var eventEntity = repository.findByUserAndAnswerMessageId(user, callbackQuery.getMessage().getMessageId()).orElseThrow(() -> new TelegramBotException("Event not found!"));
        var event = eventFactory.getEvent(eventEntity.getEventType().getCommand());
        event.init(eventEntity, user);
        return event;
    }

    public E getEvent(CallbackQuery callbackQuery) {
        return getInquiryByCallbackQuery(callbackQuery);
    }

    public EventEntity save(EventEntity e) {
        return repository.save(e);
    }
}