package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.StartInquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownConfig;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownUserConfig;
import dev.chel_shev.nelly.bot.inquiry.InquiryFactory;
import dev.chel_shev.nelly.repository.InquiryRepository;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.InquiryType.ACTION_COMMAND_MAP;
import static dev.chel_shev.nelly.util.TelegramBotUtils.isCommandInquiry;
import static dev.chel_shev.nelly.util.TelegramBotUtils.isKeyboardInquiry;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class InquiryService<I extends Inquiry> {

    private final InquiryRepository repository;
    private final UserService userService;
    private final AnswerService answerService;
    private final InquiryFactory<I> inquiryFactory;
    private final CommandService commandService;
    private final UnknownConfig unknownConfig;
    private final UnknownUserConfig unknownUserConfig;

    private I getInquiryByCallbackQuery(CallbackQuery callbackQuery) {
        var user = userService.getUserByChatId(callbackQuery.getMessage().getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
        var entity = repository.findByUserAndAndAnswerMessageId(user, callbackQuery.getMessage().getMessageId()).orElseThrow(() -> new TelegramBotException("Inquiry not found!"));
        var inquiry = inquiryFactory.getInquiry(entity.getCommand().getCommand());
        inquiry.init(entity, user);
        return inquiry;
    }

    public I getLastInquiry(Message message) {
        var user = userService.getUserByChatId(message.getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
        try {
            var entity = repository.findTopByUserOrderByDateDesc(user).orElseThrow(() -> new TelegramBotException("Inquiry not found!"));
            var inquiry = inquiryFactory.getInquiry(entity.getCommand().getCommand());
            inquiry.init(entity, user);
            if (inquiry.isClosed()) return getKeyboardInquiry(message);
            return inquiry;
        } catch (Exception e) {
            return getKeyboardInquiry(message);
        }
    }

    public I getCommandInquiry(Message message) {
        var user = userService.getUserByChatId(message.getChatId());
        String commandName;
        if (!ACTION_COMMAND_MAP.containsKey(message.getText()))
            commandName = InquiryType.getFromLabel(message.getText()).getCommand();
        else commandName = message.getText();
        var command = commandService.getCommand(commandName);
        var inquiry = inquiryFactory.getInquiry(command.getCommand());
        if (isNull(inquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownConfig));
        if (isNull(user) && !(inquiry instanceof StartInquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, unknownUserConfig));
        inquiry.init(TelegramBotUtils.getArgs(message.getText()), message.getMessageId(), !isNull(user) ? user : new UserEntity(message), command);
        return inquiry;
    }

    private I getKeyboardInquiry(Message message) {
        var user = userService.getUserByChatId(message.getChatId());
        var command = commandService.getCommand(InquiryType.KEYBOARD.getCommand());
        var inquiry = inquiryFactory.getInquiry(command.getCommand());
        inquiry.init(message.getText(), message.getMessageId(), user, command);
        return inquiry;
    }

    public I getInquiry(Message message) {
        if (isKeyboardInquiry(message.getText())) {
            return getKeyboardInquiry(message);
        } else if (isCommandInquiry(message.getText())) {
            return getCommandInquiry(message);
        } else {
            return getLastInquiry(message);
        }
    }

    public I getInquiry(CallbackQuery callbackQuery) {
        return getInquiryByCallbackQuery(callbackQuery);
    }

    public InquiryEntity save(InquiryEntity inquiry) {
        return repository.save(inquiry);
    }

    public void updateInquiry(I i, Message reply) {

    }
}