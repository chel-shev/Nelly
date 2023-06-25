package dev.chel_shev.fast.inquiry.command.workout.remove;

import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastWorkoutInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Setter
@Getter
@FastInquiryId(type = FastInquiryType.COMMAND, command = "/workout_remove", label = "\uD83E\uDD38\u200D♀ Удалить")
public class WorkoutRemoveInquiry extends FastCommandInquiry {

    private String workoutName;

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(workoutName);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastWorkoutInquiryEntity(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", workoutName=" + workoutName +
                '}';
    }
}
