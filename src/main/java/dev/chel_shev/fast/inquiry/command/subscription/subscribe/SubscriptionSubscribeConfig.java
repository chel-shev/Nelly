package dev.chel_shev.fast.inquiry.command.subscription.subscribe;

import dev.chel_shev.fast.inquiry.FastInquiryConfig;
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
@ConfigurationProperties(prefix = "inquiry.subscription-subscribe")
public class SubscriptionSubscribeConfig extends FastInquiryConfig {
    Map<String, Set<String>> answer;
}