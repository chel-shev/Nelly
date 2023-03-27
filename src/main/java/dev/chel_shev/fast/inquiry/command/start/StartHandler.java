package dev.chel_shev.fast.inquiry.command.start;

import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import dev.chel_shev.fast.service.FastUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartHandler extends FastCommandInquiryHandler<StartInquiry> {

    private final FastUserService userService;
    private final StartConfig startConfig;

    @Override
    public void executionLogic(StartInquiry i) {
        if (userService.isExist(i.getUser().getChatId())) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.SECOND, startConfig));
        } else {
            userService.save(i.getUser());
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, startConfig));
        }
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(i.getUser()));
        i.setClosed(true);
    }
}