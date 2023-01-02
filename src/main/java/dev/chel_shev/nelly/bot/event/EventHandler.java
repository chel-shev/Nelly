package dev.chel_shev.nelly.bot.event;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.EventService;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.service.WorkoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;
import static dev.chel_shev.nelly.type.KeyboardType.COMMON;
import static java.util.Objects.isNull;

@Slf4j
@Component
public abstract class EventHandler<E extends Event> {

    protected EventService<E> eventService;
    protected AnswerService aSer;
    protected UserService userService;
    protected BotResources botResources;
    protected WorkoutService workoutService;

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
        if (callbackQuery.getData().equals(CANCEL.label))
            return cancel(e);
        inlineExecutionLogic(e, callbackQuery);
        e.setId(save(e.getEntity()).getId());
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
        if (callbackQuery.getData().equals(CANCEL.label))
            return cancel(e);
        inlinePreparationLogic(e, callbackQuery);
        e.setId(save(e.getEntity()).getId());
        log.info("INLINE PREPARE {}", e);
        return e;
    }

    public E cancel(E e) {
        e.setClosed(true);
        e.setAnswerMessage("Действие отменено!");
        e.setId(save(e.getEntity()).getId());
        e.setKeyboardType(COMMON);
        log.info("CANCEL {}", e);
        return e;
    }

    public EventEntity save(EventEntity e) {
        return eventService.save(e);
    }

    public EventEntity updateEvent(E e, Message reply) {
        if (isNull(reply)) return null;
        e.setAnswerMessageId(reply.getMessageId());
        return save(e.getEntity());
    }

    @Autowired
    public final void setEventService(@Qualifier("eventService") EventService<E> eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public final void setEventService(@Qualifier("botResources") BotResources botResources) {
        this.botResources = botResources;
    }

    @Autowired
    public final void setEventService(@Qualifier("workoutService") WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @Autowired
    public final void setAnswerService(@Qualifier("answerService") AnswerService aSer) {
        this.aSer = aSer;
    }

    @Autowired
    public final void setUserService(UserService userService) {
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