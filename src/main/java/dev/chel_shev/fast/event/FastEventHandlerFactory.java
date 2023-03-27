package dev.chel_shev.fast.event;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
@RequiredArgsConstructor
public class FastEventHandlerFactory<E extends FastEvent> {

    private final Map<Class<E>, FastEventHandler<E>> registry = new HashMap<>();
    private final List<FastEventHandler<E>> eventHandlers;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (FastEventHandler<E> handler : eventHandlers) {
            Type type = handler.getClass().getGenericSuperclass();
            while (!(type instanceof ParameterizedType)) type = ((Class<?>) type).getGenericSuperclass();
            registerHandler((Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0], handler);
        }
    }

    public void registerHandler(Class<E> clazz, FastEventHandler<E> handler) {
        registry.put(clazz, handler);
    }

    public FastEventHandler<E> getHandler(Class<? extends FastEvent> clazz) {
        return registry.get(clazz);
    }
}