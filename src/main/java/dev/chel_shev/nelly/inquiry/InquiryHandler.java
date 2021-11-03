package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.inquiry.bday.BdayInquiry;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.inquiry.finance.expense.ExpenseInquiryFinance;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class InquiryHandler {

    private final InquiryService inquiryService;
    private final BotResources botResources;

    public InquiryAnswer execute(Message message) {
        Inquiry inquiry = inquiryService.getInquiry(message);
        if (inquiry instanceof InquiryFinance && !inquiry.isReadyForProcess()) {
            return ((InquiryFinance) inquiry).setAccountFromText(message.getText());
        } else if (inquiry instanceof BdayInquiry && !inquiry.isReadyForProcess()) {
            inquiry.setMessage(TelegramBotUtils.getArgs(message.getText()));
            return ((BdayInquiry) inquiry).setData();
        } else {
            if (message.hasPhoto() && inquiry instanceof ExpenseInquiryFinance) {
                inquiry.setMessage(botResources.getQRDataFromPhoto(message));
            } else if (inquiry instanceof KeyboardInquiry) {
                inquiry.setMessage(message.getText());
            } else {
                inquiry.setMessage(TelegramBotUtils.getArgs(message.getText()));
            }
            return inquiry.process();
        }
    }
}
