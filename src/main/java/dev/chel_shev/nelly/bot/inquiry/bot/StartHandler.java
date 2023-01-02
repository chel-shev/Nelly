package dev.chel_shev.nelly.bot.inquiry.bot;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartHandler extends InquiryHandler<StartInquiry> {

    private final UserService userService;
    private final StartConfig startConfig;

    @Override
    public void executionLogic(StartInquiry i) {
        if (userService.isExist(i.getUser().getChatId())) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.SECOND, startConfig));
        } else {
            userService.save(i.getUser());
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, startConfig));
        }
        i.setKeyboardType(KeyboardType.COMMON);
        i.setClosed(true);
    }
}