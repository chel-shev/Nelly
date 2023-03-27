package dev.chel_shev.fast.inquiry.command.finance.income;

import dev.chel_shev.fast.inquiry.command.FastCommandInquiryConfig;
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
@ConfigurationProperties(prefix = "inquiry.income")
public class IncomeConfig extends FastCommandInquiryConfig {
    Map<String, Set<String>> answer;
}