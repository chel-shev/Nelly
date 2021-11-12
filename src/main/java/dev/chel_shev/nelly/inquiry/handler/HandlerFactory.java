package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HandlerFactory<I extends Inquiry> {

    private final Map<Class<I>, InquiryHandler<I>> registry = new HashMap<>();
    private final List<InquiryHandler<I>> inquiryHandlers;

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
        registry.put(dataType, handlerType);
    }

    public InquiryHandler<I> getHandler(Class<? extends Inquiry> clazz) {
        return registry.get(clazz);
    }
}
