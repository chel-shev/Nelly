package dev.chel_shev.nelly.bot.utils;

import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
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
public class HandlerFactory<I extends Inquiry> {

    private final Map<Class<I>, InquiryHandler<I>> registry = new HashMap<>();
    private final List<InquiryHandler<I>> inquiryHandlers;
    private final CommandService commandService;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (InquiryHandler<I> handlerType : inquiryHandlers) {
            Type type = handlerType.getClass().getGenericSuperclass();
            while (!(type instanceof ParameterizedType)) type = ((Class<?>) type).getGenericSuperclass();
            registerHandler((Class<I>) ((ParameterizedType) type).getActualTypeArguments()[0], handlerType);
        }
    }

    public void registerHandler(Class<I> dataType, InquiryHandler<I> handlerType) {
        commandService.save(dataType.getAnnotation(InquiryId.class).value().getCommand());
        registry.put(dataType, handlerType);
    }

    public InquiryHandler<I> getHandler(Class<? extends Inquiry> clazz) {
        return registry.get(clazz);
    }
}