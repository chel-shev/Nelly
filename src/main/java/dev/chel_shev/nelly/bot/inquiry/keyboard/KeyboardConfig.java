package dev.chel_shev.nelly.bot.inquiry.keyboard;

import dev.chel_shev.nelly.bot.inquiry.InquiryConfig;
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
@ConfigurationProperties(prefix = "inquiry.keyboard")
public class KeyboardConfig extends InquiryConfig {
    Map<String, Set<String>> answer;
}