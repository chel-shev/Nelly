package dev.chel_shev.fast.inquiry.command.workout.add;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastWorkoutInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;
import dev.chel_shev.fast.type.FastPeriodType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Slf4j
@Setter
@Getter
@FastInquiryId(type = FastInquiryType.COMMAND, command = "/workout_add", label = "\uD83E\uDD38\u200D♀ Добавить")
public class WorkoutAddInquiry extends FastInquiry {

    private FastPeriodType periodType;
    private LocalDateTime workoutTime;
    private String workoutName;

    public void init(FastInquiryEntity entity, FastUserEntity userEntity) {
        super.init(entity, userEntity);
        this.periodType = ((FastWorkoutInquiryEntity) entity).getPeriodType();
        this.workoutTime = ((FastWorkoutInquiryEntity) entity).getWorkoutTime();
        this.workoutName = ((FastWorkoutInquiryEntity) entity).getWorkoutName();
        log.info("INIT {}", this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(workoutName) || isNull(workoutTime) || isNull(periodType);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastWorkoutInquiryEntity(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", periodType=" + periodType +
                ", workoutTime=" + workoutTime +
                ", workoutName=" + workoutName +
                '}';
    }
}
