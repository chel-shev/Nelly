package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventFactory;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownUserConfig;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.repository.EventRepository;
import dev.chel_shev.nelly.type.CommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class EventService<E extends Event> {

    private final EventRepository repository;
    private final UserService userService;
    private final AnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;
    private final EventFactory<E> eventFactory;

    private E getInquiryByCallbackQuery(CallbackQuery callbackQuery) {
        var user = userService.getUserByChatId(callbackQuery.getMessage().getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
        var entity = repository.findByCalendarUserAndAnswerMessageId(user, callbackQuery.getMessage().getMessageId()).orElseThrow(() -> new TelegramBotException("Event not found!"));
        var inquiry = eventFactory.getEvent(entity.getEventType().getCommand());
        inquiry.init(entity, user);
        return inquiry;
    }

    public E getEvent(CallbackQuery callbackQuery) {
        return getInquiryByCallbackQuery(callbackQuery);
    }

    public EventEntity save(EventEntity e) {
        return repository.save(e);
    }
}