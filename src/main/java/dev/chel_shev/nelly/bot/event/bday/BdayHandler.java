package dev.chel_shev.nelly.bot.event.bday;

import dev.chel_shev.nelly.bot.event.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class BdayHandler extends EventHandler<BdayEvent> {

}