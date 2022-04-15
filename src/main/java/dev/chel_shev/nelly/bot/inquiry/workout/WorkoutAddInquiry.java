package dev.chel_shev.nelly.bot.inquiry.workout;

import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.WorkoutInquiryEntity;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.InquiryType.WORKOUT_ADD;
import static java.util.Objects.isNull;

@Slf4j
@Setter
@Getter
@InquiryId(WORKOUT_ADD)
public class WorkoutAddInquiry extends Inquiry {

    private PeriodType periodType;
    private LocalDateTime workoutTime;
    private String workoutName;

    public void init(InquiryEntity entity, UserEntity user) {
        super.init(entity, user);
        this.periodType = ((WorkoutInquiryEntity) entity).getPeriodType();
        this.workoutTime = ((WorkoutInquiryEntity) entity).getWorkoutTime();
        this.workoutName = ((WorkoutInquiryEntity) entity).getWorkoutName();
        log.info("INIT {}", this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(workoutName) || isNull(workoutTime) || isNull(periodType);
    }

    @Override
    public InquiryEntity getEntity() {
        return new WorkoutInquiryEntity(this);
    }
}
