package dev.chel_shev.fast;

import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.event.FastEventHandlerFactory;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.inquiry.FastInquiryHandlerFactory;
import dev.chel_shev.fast.service.FastEventServiceImpl;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.FastInquiryService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
public class FastBot<I extends FastInquiry, E extends FastEvent> extends TelegramLongPollingBot {

    private final FastSender sender;
    private final FastInquiryService<I> inquiryService;
    private final FastEventService<E> eventService;
    private final FastInquiryHandlerFactory<I> inquiryHandlerFactory;
    private final FastEventHandlerFactory<E> eventHandlerFactory;

    @Value("${bot.api.username}")
    private String botUsername;

    FastBot(@Value("${bot.api.token}") String botToken, FastSender sender, FastInquiryService<I> inquiryService, FastEventServiceImpl<E> eventService, FastInquiryHandlerFactory<I> inquiryHandlerFactory, FastEventHandlerFactory<E> eventHandlerFactory) {
        super(botToken);
        this.sender = sender;
        this.inquiryService = inquiryService;
        this.eventService = eventService;
        this.inquiryHandlerFactory = inquiryHandlerFactory;
        this.eventHandlerFactory = eventHandlerFactory;
    }

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
                } catch (FastBotException e) {
                    processEvent(update.getCallbackQuery());
                }
            }
        } catch (FastBotException ex) {
            sender.sendMessage(ex.getChatId(), ex.getMessage(), true, false);
        }
    }

    public void processMessage(Message message) {
        Message reply;
        I i = inquiryService.getInquiry(message);
        FastInquiryHandler<I> inquiryHandler = inquiryHandlerFactory.getHandler(i.getClass());
        try {
            if (i.isNotReadyForExecute()) i = inquiryHandler.prepare(i, message);
            if (!i.isNotReadyForExecute()) i = inquiryHandler.execute(i, message);
            reply = sender.sendMessage(i.getUser().getChatId(), i.getAnswerMessage(), i.getKeyboardType(), i.getKeyboardButtons(), true, false);
        } catch (FastBotException ex) {
            reply = sender.sendMessage(ex.getChatId(), ex.getMessage(), true, false);
        }
        inquiryHandler.updateInquiry(i, reply);
    }

    public void processCallback(CallbackQuery callbackQuery) {
        I inquiry = inquiryService.getInquiry(callbackQuery);
        FastInquiryHandler<I> fastInquiryHandler = inquiryHandlerFactory.getHandler(inquiry.getClass());
        try {
            if (inquiry.isNotReadyForExecute()) inquiry = fastInquiryHandler.inlinePrepare(inquiry, callbackQuery);
            if (!inquiry.isNotReadyForExecute()) {
                inquiry = fastInquiryHandler.inlineExecute(inquiry, callbackQuery);
                sender.deleteMessage(inquiry.getUser().getChatId(), inquiry.getAnswerMessageId());
                sender.sendMessage(inquiry.getUser().getChatId(), inquiry.getAnswerMessage(), true, false);
            } else
                sender.updateMessage(inquiry.getUser().getChatId(), inquiry.getAnswerMessageId(), inquiry.getAnswerMessage(), inquiry.getKeyboardType(), inquiry.getKeyboardButtons(), true);
        } catch (FastBotException ex) {
            sender.sendMessage(ex.getChatId(), ex.getMessage(), true, false);
        }
    }

    private void processEvent(CallbackQuery callbackQuery) {
        E event = eventService.getEvent(callbackQuery);
        Message reply = null;
        FastEventHandler<E> eventHandler = eventHandlerFactory.getHandler(event.getClass());
        try {
            if (event.isNotReadyForExecute()) event = eventHandler.inlinePrepare(event, callbackQuery);
            if (!event.isNotReadyForExecute()) event = eventHandler.inlineExecute(event, callbackQuery);
            if (event.hasMedia() && event.getFile().isNew())
                reply = sender.sendPhoto(event.getUserSubscription().getFastUser().getChatId(), event.getAnswerMessage(), event.getFile(), event.getKeyboardType(), event.getKeyboardButtons(), true);
            if (event.hasMedia())
                sender.updatePhoto(event.getUserSubscription().getFastUser().getChatId(), event.getAnswerMessageId(), event.getAnswerMessage(), event.getFile(), event.getKeyboardType(), event.getKeyboardButtons(), true);
            else {
                sender.deleteMessage(event.getUserSubscription().getFastUser().getChatId(), event.getAnswerMessageId());
                sender.sendMessage(event.getUserSubscription().getFastUser().getChatId(), event.getAnswerMessage(), true, false);
            }
        } catch (FastBotException ex) {
            reply = sender.sendMessage(ex.getChatId(), ex.getMessage(), true, false);
        }
        eventHandler.updateEvent(event, reply);
    }
}