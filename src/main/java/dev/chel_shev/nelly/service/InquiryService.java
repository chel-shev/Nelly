package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.inquiry.command.StartInquiry;
import dev.chel_shev.nelly.repository.InquiryRepository;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static dev.chel_shev.nelly.util.TelegramBotUtils.isCommandInquiry;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository repository;
    private final UserService userService;
    private final AnswerService answerService;
    private final Map<String, Inquiry> inquiryList = new HashMap<>();

    public void register(String command, Inquiry inquiry) {
        inquiryList.put(command, inquiry);
    }

    public Inquiry getLast(Message message) {
        UserEntity user = userService.getUserByChatId(message.getChatId());
        if (isNull(user))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryList.get("/unknown_user")));
        InquiryEntity entity = repository.findTopByUserOrderByDateDesc(user);
        if (isNull(entity)) return null;
        Inquiry inquiry = inquiryList.get(entity.getCommand().getCommand());
        inquiry.generate(entity, user);
        return inquiry;
    }

    public Inquiry getCommandInquiry(Message message) {
        UserEntity user = userService.getUserByChatId(message.getChatId());
        Inquiry inquiry = inquiryList.get(TelegramBotUtils.getCommand(message.getText()));
        if (isNull(user) && !(inquiry instanceof StartInquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryList.get("/unknown_user")));
        if (isNull(inquiry))
            throw new TelegramBotException(answerService.generateAnswer(CommandLevel.FIRST, inquiryList.get("/unknown")));
        if (inquiry instanceof StartInquiry) {
            inquiry.generate(TelegramBotUtils.getArgs(message.getText()), LocalDateTime.now(), !isNull(user) ? user : new UserEntity(message));
            return inquiry;
        }
        inquiry.generate(TelegramBotUtils.getArgs(message.getText()), LocalDateTime.now(), user);
        return inquiry;
    }

    public Inquiry getInquiry(Message message) {
        if (isCommandInquiry(message.getText())) {
            return getCommandInquiry(message);
        } else {
            return getLast(message);
        }
    }

    public void save(Inquiry inquiry) {
        repository.save(new InquiryEntity(inquiry));
    }
}