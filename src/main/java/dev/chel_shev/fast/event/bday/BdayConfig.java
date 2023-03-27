package dev.chel_shev.fast.event.bday;

import dev.chel_shev.fast.inquiry.FastInquiryConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Getter
@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class BdayConfig extends FastInquiryConfig {
    Map<String, Set<String>> answer;
}