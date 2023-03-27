package dev.chel_shev.fast.inquiry.keyboard.subscription;

import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiryConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Component
@PropertySource(value = "classpath:/message.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "inquiry.keyboard.subscription")
public class SubscriptionKeyboardConfig extends FastKeyboardInquiryConfig {
    Map<String, Set<String>> answer;
}