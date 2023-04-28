package dev.chel_shev.fast.service;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.FastUtils;
import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryFactory;
import dev.chel_shev.fast.inquiry.command.start.StartInquiry;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastBotInquiryRepository;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FastInquiryServiceImpl<I extends FastInquiry> implements FastInquiryService<I> {

    private final FastBotInquiryRepository repository;
    private final FastInquiryFactory<I> inquiryFactory;
    private final FastCommandService commandService;
    private final FastKeyboardService keyboardService;
    private final FastUserService userService;
    private final FastUtils fastUtils;
    private final FastAnswerService answerService;
    private final UnknownUserConfig unknownUserConfig;

    public I getInquiry(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        Optional<FastUserEntity> user = userService.getFastUserByChatId(chatId);
        if (user.isEmpty())
            throw new FastBotException(answerService.generateAnswer(FastBotCommandLevel.FIRST, unknownUserConfig), chatId);
        var inquiryEntity = getInquiry(chatId, messageId);
        var inquiry = inquiryFactory.getInquiry(inquiryEntity.getCommand().getName());
        inquiry.init(inquiryEntity, user.get());
        return inquiry;
    }

    public I getLastInquiry(Message message) {
        String chatId = message.getChatId().toString();
        Optional<FastUserEntity> user = userService.getFastUserByChatId(chatId);
        if (user.isEmpty())
            throw new FastBotException(answerService.generateAnswer(FastBotCommandLevel.FIRST, unknownUserConfig), chatId);
        try {
            var inquiryEntity = getInquiry(chatId);
            I inquiry = inquiryFactory.getInquiry(inquiryEntity.getCommand().getName());
            inquiry.init(inquiryEntity, user.get());
            if (inquiry.isClosed()) return getKeyboardInquiry(message);
            return inquiry;
        } catch (Exception e) {
            return getKeyboardInquiry(message);
        }
    }

    public I getCommandInquiry(Message message) {
        String chatId = message.getChatId().toString();
        var command = commandService.getCommandByLabelOrName(message.getText());
        var inquiry = inquiryFactory.getInquiry(command.getName());
        Optional<FastUserEntity> user = userService.getFastUserByChatId(chatId);
        if (user.isEmpty() && !(inquiry instanceof StartInquiry))
            throw new FastBotException(answerService.generateAnswer(FastBotCommandLevel.FIRST, unknownUserConfig), chatId);
        inquiry.init(fastUtils.getArgs(message.getText()), message.getMessageId(), user.orElseGet(() -> new FastUserEntity(message)), command);
        return inquiry;
    }

    public I getKeyboardInquiry(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        Optional<FastUserEntity> user = userService.getFastUserByChatId(chatId);
        if (user.isEmpty())
            throw new FastBotException(answerService.generateAnswer(FastBotCommandLevel.FIRST, unknownUserConfig), chatId);
        FastCommandEntity commandByLabel = commandService.getCommandByLabel(text);
        I inquiry = inquiryFactory.getInquiry(commandByLabel.getName());
        inquiry.init(text, message.getMessageId(), user.get(), inquiry.getCommand());
        return inquiry;
    }

    public I getInquiry(Message message) {
        if (keyboardService.isKeyboardInquiry(message.getText())) {
            return getKeyboardInquiry(message);
        } else if (commandService.isCommandInquiry(message.getText())) {
            return getCommandInquiry(message);
        } else {
            return getLastInquiry(message);
        }
    }

    public I save(I inquiry) {
        Long id = repository.save(inquiry.getEntity()).getId();
        inquiry.setId(id);
        return inquiry;
    }

    public void updateInquiry(I i, Message reply) {

    }

    @Override
    public void deleteByUser(FastUserEntity user) {
        List<FastInquiryEntity> allByUserEntity = repository.findAllByUser(user);
        repository.deleteAll(allByUserEntity);
    }

    private FastInquiryEntity getInquiry(String chatId, Integer messageId) {
        return repository.findByUser_ChatIdAndAnswerMessageId(chatId, messageId).orElseThrow(() -> new FastBotException("Inquiry not found!"));
    }

    private FastInquiryEntity getInquiry(String chatId) {
        return repository.findTopByUser_ChatIdOrderByDateDesc(chatId).orElseThrow(() -> new FastBotException("Inquiry not found!"));
    }
}