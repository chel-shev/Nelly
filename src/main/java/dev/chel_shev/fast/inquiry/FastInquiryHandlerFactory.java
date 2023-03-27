package dev.chel_shev.fast.inquiry;

import dev.chel_shev.fast.service.FastCommandService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FastInquiryHandlerFactory<I extends FastInquiry> {

    private final Map<Class<I>, FastInquiryHandler<I>> registry = new HashMap<>();
    private final List<FastInquiryHandler<I>> inquiryHandlers;
    private final FastCommandService commandService;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (FastInquiryHandler<I> handlerType : inquiryHandlers) {
            Type type = handlerType.getClass().getGenericSuperclass();
            while (!(type instanceof ParameterizedType)) type = ((Class<?>) type).getGenericSuperclass();
            registerHandler((Class<I>) ((ParameterizedType) type).getActualTypeArguments()[0], handlerType);
        }
    }

    public void registerHandler(Class<I> dataType, FastInquiryHandler<I> handlerType) {
        log.info(String.valueOf(dataType));
        FastInquiryId inquiryId = dataType.getAnnotation(FastInquiryId.class);
        if (null != inquiryId)
            commandService.save(inquiryId.command(), inquiryId.label(), inquiryId.type());
        registry.put(dataType, handlerType);
    }

    public FastInquiryHandler<I> getHandler(Class<? extends FastInquiry> clazz) {
        return registry.get(clazz);
    }
}