package dev.chel_shev.nelly.bot.event;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventHandler;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
@RequiredArgsConstructor
public class EventHandlerFactory<E extends Event> {

    private final Map<Class<E>, EventHandler<E>> registry = new HashMap<>();
    private final List<EventHandler<E>> eventHandlers;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (EventHandler<E> handlerType : eventHandlers) {
            Type type = handlerType.getClass().getGenericSuperclass();
            while (!(type instanceof ParameterizedType)) type = ((Class<?>) type).getGenericSuperclass();
            registerHandler((Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0], handlerType);
        }
    }

    public void registerHandler(Class<E> dataType, EventHandler<E> handlerType) {
        registry.put(dataType, handlerType);
    }

    public EventHandler<E> getEvent(Class<? extends Event> clazz) {
        return registry.get(clazz);
    }
}