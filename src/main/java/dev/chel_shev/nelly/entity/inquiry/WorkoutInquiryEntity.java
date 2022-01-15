package dev.chel_shev.nelly.entity.inquiry;

import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutRemoveInquiry;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry_workout")
@NoArgsConstructor
public class WorkoutInquiryEntity extends InquiryEntity {

    private PeriodType periodType;
    private LocalDateTime workoutTime;
    private String workoutName;

    public WorkoutInquiryEntity(WorkoutRemoveInquiry inquiry) {
        super(inquiry);
        this.workoutName = inquiry.getWorkoutName();
    }

    public WorkoutInquiryEntity(WorkoutAddInquiry inquiry) {
        super(inquiry);
        this.periodType = inquiry.getPeriodType();
        this.workoutTime = inquiry.getWorkoutTime();
        this.workoutName = inquiry.getWorkoutName();
    }
}