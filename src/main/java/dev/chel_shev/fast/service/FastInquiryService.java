package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FastInquiryService<I extends FastInquiry> {

    I getLastInquiry(Message message);
    I getCommandInquiry(Message message);
    I getKeyboardInquiry(Message message);
    I getInquiry(Message message);
    I getInquiry(CallbackQuery callbackQuery);
    I save(I inquiry);
    void updateInquiry(I i, Message reply);

    void deleteByUser(FastUserEntity userEntity);
}