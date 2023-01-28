package dev.chel_shev.nelly.bot.inquiry.finance;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.entity.event.finance.AccountEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

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
    public void preparationLogic(IF i, Message message) {
        if (nonNull(i.getAccount())) {
            if (message.hasPhoto()) i.setMessage(botResources.getQRDataFromPhoto(message));
            else i.setMessage(message.getText());
        } else if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage("Выберите счет, с которым будет производится операция:");
            i.setKeyboardType(ACCOUNT_LIST);
        } else {
            AccountEntity account = getAccount(i, message.getText().split(" ")[1]);
            i.setAccount(account);
            i.setAnswerMessage(getTextInfo(i));
            i.setKeyboardType(CANCEL);
        }
    }

    public abstract String getTextInfo(IF i);

    public IF cancel(IF i) {
        super.cancel(i);
        i.setKeyboardType(FINANCE);
        return i;
    }

    public AccountEntity getAccount(IF i, String nameAccount) {
        return userService.getAccountList(i.getUser().getId()).stream()
                .filter(e -> e.getName().equals(nameAccount))
                .findFirst()
                .orElseThrow(() -> new TelegramBotException(i.getUser(), "'" + nameAccount + "' не найден!", KeyboardType.CANCEL));
    }
}
