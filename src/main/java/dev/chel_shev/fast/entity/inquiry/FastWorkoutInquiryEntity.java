package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.command.workout.add.WorkoutAddInquiry;
import dev.chel_shev.fast.inquiry.command.workout.remove.WorkoutRemoveInquiry;
import dev.chel_shev.fast.type.FastPeriodType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_workout")
@NoArgsConstructor
public class FastWorkoutInquiryEntity extends FastInquiryEntity {

    private FastPeriodType periodType;
    private LocalDateTime workoutTime;
    private String workoutName;

    public FastWorkoutInquiryEntity(WorkoutRemoveInquiry inquiry) {
        super(inquiry);
        this.workoutName = inquiry.getWorkoutName();
    }

    public FastWorkoutInquiryEntity(WorkoutAddInquiry inquiry) {
        super(inquiry);
        this.periodType = inquiry.getPeriodType();
        this.workoutTime = inquiry.getWorkoutTime();
        this.workoutName = inquiry.getWorkoutName();
    }
}