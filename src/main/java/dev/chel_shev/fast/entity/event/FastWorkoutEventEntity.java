package dev.chel_shev.fast.entity.event;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.type.FastPeriodType;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_event_workout")
@NoArgsConstructor
public class FastWorkoutEventEntity extends FastEventEntity {

    private Integer step;
    private Integer level;

    @ManyToOne
    private WorkoutEntity workout;

    public FastWorkoutEventEntity(int step, int level, FastCommandEntity command, WorkoutEntity workout, FastPeriodType periodType, LocalDateTime eventDateTime, FastUserSubscriptionEntity userSubscription) {
        super(command, periodType, eventDateTime, userSubscription);
        this.step = step;
        this.level = level;
        this.workout = workout;
    }

    public FastWorkoutEventEntity(FastWorkoutEvent event) {
        super(event);
        this.step = event.getStep();
        this.level = event.getLevel();
        this.workout = event.getWorkout();
    }
}