package dev.chel_shev.fast.event;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
@RequiredArgsConstructor
public class FastEventFactory<E extends FastEvent> {

    private final Map<String, Class<E>> command2event = new HashMap<>();
    private final List<E> events;
    private final ApplicationContext applicationContext;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (E e : events) {
            Class<E> clazz = (Class<E>) e.getClass();
            String command = null;
            FastEventId eventId = clazz.getAnnotation(FastEventId.class);
            if (null != eventId)
                command = eventId.command();
            command2event.put(command, clazz);
        }
    }

    public E getEvent(String command) {
        return applicationContext.getBean(command2event.get(command));
    }
}