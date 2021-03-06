package dev.chel_shev.nelly.bot.event;

import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.InquiryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventId {
    EventType value();
}
