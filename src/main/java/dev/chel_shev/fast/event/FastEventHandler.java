package dev.chel_shev.fast.event;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.service.FastAnswerService;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.FastKeyboardService;
import dev.chel_shev.fast.service.FastUserService;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static java.util.Objects.isNull;

@Slf4j
@Component
public abstract class FastEventHandler<E extends FastEvent> {

    protected FastEventService<E> eventService;
    protected FastAnswerService answerService;
    protected FastUserService userService;
    protected FastBotResource botResources;
    protected FastKeyboardService keyboardService;

    /**
     * Event execution logic
     *
     * @param e - Received event
     * @return - Processed event
     */
    protected void executionLogic(E e) {
    }

    /**
     * Event preparation logic
     *
     * @param e       - Received event
     * @param message - Bot message
     * @return - Processed event
     */
    protected void preparationLogic(E e, Message message) {
    }

//    public E execute(E e, Message message) {
//        if (message.getText().equals(CANCEL.label))
//            return cancel(e);
//        executionLogic(e);
//        e.setId(save(e.getEntity()).getId());
//        log.info("EXECUTE {}", e);
//        return e;
//    }

    public E inlineExecute(E e, CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("Отмена"))
            return cancel(e);
        inlineExecutionLogic(e, callbackQuery);
        e.setId(eventService.save(e));
        log.info("INLINE EXECUTE {}", e);
        return e;
    }

//    public E prepare(E e, Message message) {
//        if (message.getText().equals(CANCEL.label))
//            return cancel(e);
//        preparationLogic(e, message);
//        e.setId(save(e.getEntity()).getId());
//        log.info("PREPARE {}", e);
//        return e;
//    }

    public E inlinePrepare(E e, CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("Отмена"))
            return cancel(e);
        inlinePreparationLogic(e, callbackQuery);
        e.setId(eventService.save(e));
        log.info("INLINE PREPARE {}", e);
        return e;
    }

    public E cancel(E e) {
        e.setClosed(true);
        e.setAnswerMessage("Действие отменено!");
        e.setId(eventService.save(e));
        e.setKeyboardType(FastKeyboardType.REPLY);
        log.info("CANCEL {}", e);
        return e;
    }

    public E updateEvent(E e, Message reply) {
        if (isNull(reply)) return null;
        e.setAnswerMessageId(reply.getMessageId());
        e.setId(eventService.save(e));
        return e;
    }

    @Autowired
    public final void setEventService(@Qualifier("fastEventServiceImpl") FastEventService<E> eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public final void setFastBotResource(@Qualifier("fastBotResource") FastBotResource botResources) {
        this.botResources = botResources;
    }

    @Autowired
    public final void setFastBotResource(FastKeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Autowired
    public final void setAnswerService(@Qualifier("fastAnswerService") FastAnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public final void setUserService(FastUserService userService) {
        this.userService = userService;
    }

//    public void updateEvent(E e, Message reply) {
//        if (null == e) return;
//        e.setAnswerMessage(reply.getText());
//        e.setAnswerMessageId(reply.getMessageId());
//        save(e.getEntity());
//        log.info("UPDATE {}", e);
//    }

    public void inlineExecutionLogic(E e, CallbackQuery callbackQuery) {
    }

    public void inlinePreparationLogic(E e, CallbackQuery callbackQuery) {
    }
}