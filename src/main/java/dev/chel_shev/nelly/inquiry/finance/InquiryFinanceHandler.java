package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.CommandLevel.THIRD;
import static dev.chel_shev.nelly.type.KeyboardType.*;
import static java.util.Objects.nonNull;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public abstract class InquiryFinanceHandler<IF extends InquiryFinance> extends InquiryHandler<IF> {

    @Autowired
    protected BotResources botResources;

    @Override
    public IF preparationLogic(IF i, Message message) {
        if (nonNull(i.getAccount())) {
            if (message.hasPhoto()) i.setMessage(botResources.getQRDataFromPhoto(message));
            else i.setMessage(message.getText());
        } else if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage("Выберите счет, с которым будет производится операция:");
            i.setKeyboardType(ACCOUNTS);
        } else {
            AccountEntity account = getAccount(i, message.getText().split(" ")[1]);
            i.setAccount(account);
            i.setAnswerMessage(getTextInfo(i));
            i.setKeyboardType(CANCEL);
        }
        return i;
    }

    public String getTextInfo(IF i) {
        return answerService.generateAnswer(THIRD, i);
    }

    public IF cancel(IF i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setKeyboardType(FINANCE);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    public AccountEntity getAccount(IF i, String nameAccount) {
        return userService.getAccountList(i.getUser().getId()).stream()
                .filter(e -> e.getName().equals(nameAccount))
                .findFirst()
                .orElseThrow(() -> new TelegramBotException(i.getUser(), "'" + nameAccount + "' не найден!", KeyboardType.CANCEL));
    }
}
