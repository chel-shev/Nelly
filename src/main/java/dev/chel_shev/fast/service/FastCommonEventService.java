package dev.chel_shev.fast.service;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventFactory;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.type.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static dev.chel_shev.fast.type.FastBotCommandLevel.FIRST;

@Service
@RequiredArgsConstructor
@Primary
public class FastCommonEventService<E extends FastEvent> implements FastEventService<E> {

    private final FastEventRepository repository;
    private final FastUserService userService;
    private final FastAnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;
    private final FastEventFactory<E> eventFactory;
    private final UserSubscriptionRepository userSubscriptionRepository;


    public void removeEvent() {
//        List<FastBdayEventEntity> bdayEntities = bdayEventRepository.findByNameAndUserSubscriptionUser(name, user);
//        repository.deleteAll(bdayEntities);
    }

    public boolean isExist(String name, String chatId, LocalDateTime date) {
//        return bdayEventRepository.existsByNameAndDateAndUserSubscriptionUser(name, date, chatId);
        return true;
    }

    public void deleteByChatId(String chatId) {
//        List<EventEntity> allByUser = repository.findAllByUserSubscriptionUser(user);
//        repository.deleteAll(allByUser);
    }

    @Override
    public void updateEvent(FastEventEntity eventEntity) {
    }

    @Override
    public boolean isExist(String name, FastUserEntity userEntity, LocalDateTime date) {
        return false;
    }

    public E getEvent(CallbackQuery callbackQuery) {
        Optional<FastUserEntity> user = userService.getFastUserByChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
        if (user.isEmpty())
            throw new FastBotException(answerService.generateAnswer(FIRST, unknownUserConfig));
        var eventEntity = repository.findByUser_FastUserAndAnswerMessageId(user.get(), callbackQuery.getMessage().getMessageId()).orElseThrow(() -> new FastBotException("Event not found!"));
        FastUserSubscriptionEntity subscriptionEntity = userSubscriptionRepository.findByFastUser_ChatIdAndCommandAndTypeIn(user.get().getChatId(), eventEntity.getUser().getCommand(), Collections.singleton(SubscriptionType.MAIN));
        var event = eventFactory.getEvent(eventEntity.getUser().getCommand().getName());
        event.init(eventEntity, subscriptionEntity);
        return event;
    }

    public Long save(E e) {
        return repository.save(e.getEntity()).getId();
    }

    @Override
    public Long save(FastEventEntity e) {
        return repository.save(e).getId();
    }

    @Override
    public void deleteByUser(FastUserEntity userEntity) {

    }
}