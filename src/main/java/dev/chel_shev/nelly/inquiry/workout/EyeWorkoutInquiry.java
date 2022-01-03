package dev.chel_shev.nelly.inquiry.workout;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.WORKOUT)
public class EyeWorkoutInquiry extends Inquiry {

    @Override
    public EyeWorkoutInquiry getInstance() {
        return new EyeWorkoutInquiry();
    }
}
