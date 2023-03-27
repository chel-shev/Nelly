package dev.chel_shev.fast.inquiry.keyboard.workout;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkoutKeyboardHandler extends FastInquiryHandler<WorkoutKeyboardInquiry> {

    private final WorkoutKeyboardConfig workoutKeyboardConfig;
    @Override
    public void executionLogic(WorkoutKeyboardInquiry i) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(WorkoutKeyboardInquiry.class));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutKeyboardConfig));
    }
}
