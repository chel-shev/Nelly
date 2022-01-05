package dev.chel_shev.nelly.inquiry.workout;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static dev.chel_shev.nelly.type.InquiryType.WORKOUT;

@InquiryId(WORKOUT)
public class EyeWorkoutInquiry extends Inquiry {
}
