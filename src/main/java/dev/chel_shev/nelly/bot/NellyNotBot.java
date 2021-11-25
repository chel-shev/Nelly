package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.inquiry.utils.HandlerFactory;
import dev.chel_shev.nelly.repository.ExerciseRepository;
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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

import static java.util.Objects.isNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@RequiredArgsConstructor
public class NellyNotBot<I extends Inquiry> extends TelegramLongPollingBot {

    private final ExerciseRepository exerciseRepository;
    private final BotSender sender;
    private final InquiryService<I> inquiryService;
    private final HandlerFactory<I> handlerFactory;

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
        if (isNull(update.getMessage())) return;
        Message message = update.getMessage();
        try {
            I i = inquiryService.getInquiry(message);
            InquiryHandler<I> inquiryHandler = handlerFactory.getHandler(i.getClass());
            if (i.isNotReadyForExecute()) i = inquiryHandler.prepare(i, message);
            if (!i.isNotReadyForExecute()) i = inquiryHandler.execute(i, message);
            sender.sendMessage(i);
        } catch (TelegramBotException e) {
            sender.sendMessage(e.getUser(), KeyboardType.CANCEL, e.getMessage());
        }
    }
}