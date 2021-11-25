package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.bot.StartInquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryFactory;
import dev.chel_shev.nelly.repository.InquiryRepository;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public I getLastInquiry(Message message) {
        var user = userService.getUserByChatId(message.getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryFactory.create("/unknown_user")));
        try {
            var entity = repository.findTopByUserOrderByDateDesc(user).orElseThrow(() -> new TelegramBotException("Inquiry not found!"));
            var inquiry = inquiryFactory.create(entity.getCommand().getCommand());
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
        var inquiry = inquiryFactory.create(command.getCommand());
        if (isNull(inquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryFactory.create("/unknown")));
        if (isNull(user) && !(inquiry instanceof StartInquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryFactory.create("/unknown_user")));
        inquiry.init(TelegramBotUtils.getArgs(message.getText()), !isNull(user) ? user : new UserEntity(message), command);
        return inquiry;
    }

    private I getKeyboardInquiry(Message message) {
        var user = userService.getUserByChatId(message.getChatId());
        var command = commandService.getCommand(InquiryType.KEYBOARD.getCommand());
        var inquiry = inquiryFactory.create(command.getCommand());
        inquiry.init(message.getText(), user, command);
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

    public InquiryEntity save(InquiryEntity inquiry) {
        return repository.save(inquiry);
    }
}