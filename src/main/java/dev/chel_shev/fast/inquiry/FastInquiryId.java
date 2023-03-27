package dev.chel_shev.fast.inquiry;

import dev.chel_shev.fast.type.FastInquiryType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FastInquiryId {
    FastInquiryType type();
    String command();
    String label();
    Class<? extends FastInquiry>[] buttons() default {};
}
