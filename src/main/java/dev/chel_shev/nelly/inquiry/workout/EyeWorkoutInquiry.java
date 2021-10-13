package dev.chel_shev.nelly.inquiry.workout;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;


@InquiryId(type = InquiryType.WORKOUT, command = "/eye_workout")
public class EyeWorkoutInquiry extends Inquiry {

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        return null;
    }
}
