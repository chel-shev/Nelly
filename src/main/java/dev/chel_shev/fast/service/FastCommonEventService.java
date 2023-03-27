package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventFactory;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Primary
public class FastCommonEventService<E extends FastEvent> implements FastEventService<E> {

    private final FastEventRepository repository;
    private final FastUserService userService;
    private final FastAnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;
    private final FastEventFactory<E> eventFactory;


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
//        var userSubscription = userSubscriptionService.getUserByChatId(callbackQuery.getMessage().getChatId());
//        if (null == userSubscription || null == userSubscription.getUser())
//            throw new FastBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
//        var eventEntity = repository.findByUserSubscriptionUserAndAnswerMessageId(userSubscription.getUser(), callbackQuery.getMessage().getMessageId()).orElseThrow(() -> new FastBotException("Event not found!"));
//        var event = eventFactory.getEvent(eventEntity.getEventType().getCommand());
//        event.init(eventEntity, userSubscription);
        return null;
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