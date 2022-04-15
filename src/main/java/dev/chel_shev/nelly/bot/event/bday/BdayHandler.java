package dev.chel_shev.nelly.bot.event.bday;

import dev.chel_shev.nelly.bot.event.EventHandler;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.bot.inquiry.bday.BdayAddConfig;
import dev.chel_shev.nelly.bot.inquiry.bday.BdayAddInquiry;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.bot.utils.InquiryUtils.getLastArgsPast;
import static dev.chel_shev.nelly.type.KeyboardType.BDAY;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class BdayHandler extends EventHandler<BdayEvent> {

}