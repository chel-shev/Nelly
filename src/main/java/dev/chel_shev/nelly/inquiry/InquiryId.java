package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.type.InquiryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InquiryId {
    InquiryType type();

    String command() default "";
}
