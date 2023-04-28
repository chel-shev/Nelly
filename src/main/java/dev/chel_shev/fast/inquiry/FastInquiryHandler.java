package dev.chel_shev.fast.inquiry;

import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.FastUtils;
import dev.chel_shev.fast.service.FastAnswerService;
import dev.chel_shev.fast.service.FastInquiryService;
import dev.chel_shev.fast.service.FastKeyboardService;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public abstract class FastInquiryHandler<I extends FastInquiry> {

    protected FastInquiryService<I> inquiryService;
    protected FastAnswerService answerService;
    protected FastKeyboardService keyboardService;
    protected FastUtils fastUtils;
    protected FastSender sender;

    /**
     * Inquiry execution logic
     *
     * @param i - Received inquiry
     * @return - Processed inquiry
     */
    protected void executionLogic(I i, Message message) {
    }

    /**
     * Inquiry preparation logic
     *
     * @param i       - Received inquiry
     * @param message - Bot message
     * @return - Processed inquiry
     */
    protected void preparationLogic(I i, Message message) {
    }

    public I execute(I i, Message message) {
        if (message.getText().equals("Отмена"))
            return cancel(i);
        executionLogic(i, message);
        save(i);
        log.info("EXECUTE {}", i);
        return i;
    }

    public I inlineExecute(I i, CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("Отмена"))
            return cancel(i);
        inlineExecutionLogic(i, callbackQuery);
        save(i);
        log.info("INLINE EXECUTE {}", i);
        return i;
    }

    public I prepare(I i, Message message) {
        if (message.getText().equals("Отмена"))
            return cancel(i);
        preparationLogic(i, message);
        save(i);
        log.info("PREPARE {}", i);
        return i;
    }

    public I inlinePrepare(I i, CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("Отмена"))
            return cancel(i);
        inlinePreparationLogic(i, callbackQuery);
        save(i);
        log.info("INLINE PREPARE {}", i);
        return i;
    }


    public I cancel(I i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        save(i);
        i.setKeyboardType(FastKeyboardType.REPLY);
        log.info("CANCEL {}", i);
        return i;
    }

    public I save(I i) {
        return inquiryService.save(i);
    }

    @Autowired
    public final void setInquiryService(FastInquiryService<I> inquiryService) {
        this.inquiryService = inquiryService;
    }

    @Autowired
    public final void setInquiryService(FastKeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Autowired
    public final void setAnswerService(FastAnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public final void setFastUtils(FastUtils fastUtils) {
        this.fastUtils = fastUtils;
    }

    @Autowired
    public final void setFastSender(FastSender sender) {
        this.sender = sender;
    }

    public void updateInquiry(I i, Message reply) {
        if (null == i) return;
        i.setAnswerMessage(reply.getText());
        i.setAnswerMessageId(reply.getMessageId());
        try {
            save(i);
        } catch (Exception e) {
            log.info("NOT FOUND {}", i);
        }
        log.info("UPDATE {}", i);
    }

    public void closeLastInquiry(Message m) {
        I lastInquiry = inquiryService.getLastInquiry(m);
        if (!lastInquiry.isClosed())
            sender.deleteMessage(lastInquiry.getUser().getChatId(), lastInquiry.getAnswerMessageId());
    }

    public void inlineExecutionLogic(I i, CallbackQuery callbackQuery) {
    }

    public void inlinePreparationLogic(I i, CallbackQuery callbackQuery) {
    }
}