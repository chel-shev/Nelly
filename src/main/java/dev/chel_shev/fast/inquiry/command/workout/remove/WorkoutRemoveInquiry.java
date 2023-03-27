package dev.chel_shev.fast.inquiry.command.workout.remove;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@FastInquiryId(type = FastInquiryType.COMMAND, command = "/workout_remove", label = "\uD83E\uDD38\u200D♀ Удалить")
public class WorkoutRemoveInquiry extends FastCommandInquiry {

    private String workoutName;
}
