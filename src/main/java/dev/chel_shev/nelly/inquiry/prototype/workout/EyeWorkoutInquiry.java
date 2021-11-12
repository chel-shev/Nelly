package dev.chel_shev.nelly.inquiry.prototype.workout;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.WORKOUT, command = "/eye_workout")
public class EyeWorkoutInquiry extends Inquiry {

    @Override
    public void initAnswers() {

    }
}
