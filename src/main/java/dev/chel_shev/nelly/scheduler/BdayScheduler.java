package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.TelegramBotMain;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.repository.UserRepository;
import dev.chel_shev.nelly.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static dev.chel_shev.nelly.util.DateTimeUtils.itsToday;

@EnableScheduling
@Slf4j
@Component
@RequiredArgsConstructor
public class BdayScheduler {

    private final UserRepository userRepository;
    private final TelegramBotMain botMain;

    @Scheduled(cron = DateTimeUtils.FIRST_SECOND_OF_THE_DAY)
    public void senderBirthdays() {
        userRepository.findAll().forEach(this::sentNotification);
    }

    private void sentNotification(UserEntity user) {
        user.getBdayList().forEach(e -> {
            if (itsToday(e.getDate()))
                botMain.sendMessage(new InquiryAnswer(user, e.getName() + " празднует свой День Рождения, поздравь, если уместно!", KeyboardType.NONE));
        });
    }
}