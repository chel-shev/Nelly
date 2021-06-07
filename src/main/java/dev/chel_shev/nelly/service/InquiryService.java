package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.command.StartInquiry;
import dev.chel_shev.nelly.repository.InquiryRepository;
import dev.chel_shev.nelly.util.TelegramBotUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static dev.chel_shev.nelly.util.TelegramBotUtil.isCommandInquiry;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository repository;
    private final UserService userService;
    private final Map<String, Inquiry> inquiryList = new HashMap<>();

    public void register(String command, Inquiry inquiry) {
        inquiryList.put(command, inquiry);
    }

    public Inquiry getLast(Message message) {
        UserEntity user = userService.getUserByChatId(message.getChatId());
        InquiryEntity entity = repository.findTopByUserOrderByDateDesc(user);
        if (isNull(entity)) return null;
        Inquiry inquiry = inquiryList.get(entity.getCommand());
        inquiry.generate(entity, user);
        return inquiry;
    }

    public Inquiry getCommandInquiry(Message message) {
        Inquiry inquiry = inquiryList.get(TelegramBotUtil.getCommand(message.getText()));
        if (isNull(inquiry)) throw new TelegramBotException("Command not found!");
        if (inquiry instanceof StartInquiry) {
            inquiry.generate(TelegramBotUtil.getArgs(message.getText()), LocalDateTime.now(),
                    new UserEntity(null, message.getFrom().getFirstName(), message.getFrom().getLastName(), message.getFrom().getUserName(), message.getChatId()));
            return inquiry;
        }
        inquiry.generate(TelegramBotUtil.getArgs(message.getText()), LocalDateTime.now(), userService.getUserByChatId(message.getChatId()));
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