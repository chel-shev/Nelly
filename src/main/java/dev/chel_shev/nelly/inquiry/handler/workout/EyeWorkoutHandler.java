package dev.chel_shev.nelly.inquiry.handler.workout;

import dev.chel_shev.nelly.inquiry.handler.InquiryHandler;
import dev.chel_shev.nelly.inquiry.prototype.workout.EyeWorkoutInquiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class EyeWorkoutHandler extends InquiryHandler<EyeWorkoutInquiry> {

    @Override
    public EyeWorkoutInquiry executionLogic(EyeWorkoutInquiry inquiry) {
        return null;
    }

    @Override
    public EyeWorkoutInquiry preparationLogic(EyeWorkoutInquiry eyeWorkoutInquiry, Message message) {
        return null;
    }
}
