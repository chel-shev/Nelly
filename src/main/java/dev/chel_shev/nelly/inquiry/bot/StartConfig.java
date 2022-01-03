package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.InquiryConfig;
import dev.chel_shev.nelly.inquiry.bday.BdayAddInquiry;
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
@ConfigurationProperties(prefix = "inquiry.start")
public class StartConfig extends InquiryConfig {
    Map<String, Set<String>> answer;
}