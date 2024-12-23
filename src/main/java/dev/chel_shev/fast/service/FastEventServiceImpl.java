package dev.chel_shev.fast.service;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.event.FastWordEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventFactory;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.event.FastEventHandlerFactory;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.type.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dev.chel_shev.fast.type.FastBotCommandLevel.FIRST;

@Service
@RequiredArgsConstructor
@Primary
public class FastEventServiceImpl<E extends FastEvent> implements FastEventService<E> {

    private final FastEventRepository repository;
    private final FastUserService userService;
    private final FastAnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;
    private final UserSubscriptionRepository userSubscriptionRepository;
    protected FastEventFactory<E> eventFactory;

    @Autowired
    public final void setFastEventFactory(FastEventFactory<E> eventFactory) {
        this.eventFactory = eventFactory;
    }

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
    public void initNextEvent(E e) {
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
        var event = getEvent(eventEntity.getUser().getCommand().getName());
        event.init(eventEntity, subscriptionEntity);
        return event;
    }

    public E getEvent(String command){
        return eventFactory.getEvent(command);
    }

    public Long save(E e) {
        return repository.save(e.getEntity()).getId();
    }

    @Override
    public Long save(FastEventEntity e) {
        return repository.save(e).getId();
    }

    @Override
    public List<FastEventEntity> getAllEvents() {
        return repository.findAll().stream().filter(e -> e instanceof FastWordEventEntity).toList();

    }

    @Override
    public void deleteByUserSubscription(FastUserSubscriptionEntity userEntity) {
        List<FastEventEntity> allByUser = repository.findAllByUser(userEntity);
        repository.deleteAllInBatch(allByUser);
    }

    @Override
    public void deleteByUser(FastUserEntity user) {
        List<FastEventEntity> allByUser = repository.findAllByUser_FastUser(user);
        repository.deleteAll(allByUser);
    }

    @Override
    public void delete(FastEventEntity eventEntity) {
        repository.delete(eventEntity);
    }
}