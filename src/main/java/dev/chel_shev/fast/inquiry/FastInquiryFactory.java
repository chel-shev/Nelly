package dev.chel_shev.fast.inquiry;

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
public class FastInquiryFactory<I extends FastInquiry> {

    private final Map<String, Class<I>> command2inquiry = new HashMap<>();
    private final List<I> inquiries;
    private final ApplicationContext applicationContext;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init() {
        for (I i : inquiries) {
            Class<I> clazz = (Class<I>) i.getClass();
            String command = null;
            FastInquiryId inquiryId = clazz.getAnnotation(FastInquiryId.class);
            if (null != inquiryId)
                command = inquiryId.command();
            command2inquiry.put(command, clazz);
        }
    }

    public I getInquiry(String command) {
        return applicationContext.getBean(command2inquiry.get(command));
    }
}
