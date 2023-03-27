package dev.chel_shev.fast.inquiry.command.finance;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public abstract class InquiryFinanceHandler<IF extends InquiryFinance> extends FastCommandInquiryHandler<IF> {

    @Autowired
    protected FastBotResource botResource;

    @Override
    public void preparationLogic(IF i, Message message) {
        if (nonNull(i.getAccount())) {
            if (message.hasPhoto()) i.setMessage(botResource.getQRDataFromPhoto(message));
            else i.setMessage(message.getText());
        } else if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage("Выберите счет, с которым будет производится операция:");
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(Arrays.asList("аккаунты"));
        } else {
            AccountEntity account = getAccount(i, message.getText().split(" ")[1]);
            i.setAccount(account);
            i.setAnswerMessage(getTextInfo(i));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(Arrays.asList("Отмена"));
        }
    }

    public abstract String getTextInfo(IF i);

    public IF cancel(IF i) {
        super.cancel(i);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("FINANCE"));
        return i;
    }

    public AccountEntity getAccount(IF i, String nameAccount) {
        return null;
//        return userService.getAccountList(i.getUser().getId()).stream()
//                .filter(e -> e.getName().equals(nameAccount))
//                .findFirst()
//                .orElseThrow(() -> new FastBotException(i.getUser(), "'" + nameAccount + "' не найден!", KeyboardType.CANCEL));
    }
}
