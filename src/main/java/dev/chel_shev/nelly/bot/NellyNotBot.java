package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventHandler;
import dev.chel_shev.nelly.bot.event.EventHandlerFactory;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.bot.utils.HandlerFactory;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.repository.event.workout.WorkoutExercisesRepository;
import dev.chel_shev.nelly.service.EventService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@RequiredArgsConstructor
public class NellyNotBot<I extends Inquiry, E extends Event> extends TelegramLongPollingBot {

    private final WorkoutExercisesRepository workoutExercisesRepository;
    private final BotSender sender;
    private final InquiryService<I> inquiryService;
    private final EventService<E> eventService;
    private final HandlerFactory<I> handlerFactory;
    private final EventHandlerFactory<E> eventFactory;

    @Value("${bot.api.username}")
    private String botUsername;
    @Value("${bot.api.token}")
    private String botToken;

    @PostConstruct
    private void register() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                processMessage(update.getMessage());
            } else if (update.hasCallbackQuery()) {
                try {
                    processCallback(update.getCallbackQuery());
                } catch (TelegramBotException e) {
                    processEvent(update.getCallbackQuery());
                }
            }
        } catch (TelegramBotException e) {
            sender.sendMessage(e.getUser(), KeyboardType.CANCEL, e.getMessage(), true);
        }
    }

    public void processMessage(Message message) {
        Message reply;
        I i = inquiryService.getInquiry(message);
        InquiryHandler<I> inquiryHandler = handlerFactory.getHandler(i.getClass());
        try {
            if (i.isNotReadyForExecute()) i = inquiryHandler.prepare(i, message);
            if (!i.isNotReadyForExecute()) i = inquiryHandler.execute(i, message);
            reply = sender.sendMessage(i);
        } catch (TelegramBotException e) {
            reply = sender.sendMessage(e.getUser(), KeyboardType.CANCEL, e.getMessage(), true);
        }
        inquiryHandler.updateInquiry(i, reply);
    }

    public void processCallback(CallbackQuery callbackQuery) {
        I i = inquiryService.getInquiry(callbackQuery);
        InquiryHandler<I> inquiryHandler = handlerFactory.getHandler(i.getClass());
        try {
            if (i.isNotReadyForExecute()) i = inquiryHandler.inlinePrepare(i, callbackQuery);
            if (!i.isNotReadyForExecute()) {
                i = inquiryHandler.inlineExecute(i, callbackQuery);
                sender.deleteMessage(i);
                sender.sendMessage(i);
            } else
                sender.updateMessage(i);
        } catch (TelegramBotException e) {
            sender.sendMessage(e.getUser(), KeyboardType.CANCEL, e.getMessage(), true);
        }
    }

    private void processEvent(CallbackQuery callbackQuery) {
        E e = eventService.getEvent(callbackQuery);
        Message reply;
        EventHandler<E> eventHandler = eventFactory.getEvent(e.getClass());
        try {
            if (e.isNotReadyForExecute()) e = eventHandler.inlinePrepare(e, callbackQuery);
            if (!e.isNotReadyForExecute()) {
                e = eventHandler.inlineExecute(e, callbackQuery);
            }
            reply = sender.sendMessage(e);
        } catch (TelegramBotException ex) {
            reply = sender.sendMessage(ex.getUser(), KeyboardType.CANCEL, ex.getMessage(), true);
        }
        eventHandler.updateEvent(e, reply);
    }
}