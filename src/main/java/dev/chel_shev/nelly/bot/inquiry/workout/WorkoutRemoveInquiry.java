package dev.chel_shev.nelly.bot.inquiry.workout;

import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import lombok.Getter;
import lombok.Setter;

import static dev.chel_shev.nelly.type.InquiryType.WORKOUT_REMOVE;

@Setter
@Getter
@InquiryId(WORKOUT_REMOVE)
public class WorkoutRemoveInquiry extends Inquiry {

    private String workoutName;
}
