package dev.chel_shev.nelly.entity.event;

import dev.chel_shev.nelly.bot.event.workout.WorkoutEvent;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "event_workout")
@NoArgsConstructor
public class WorkoutEventEntity extends EventEntity {

    private Integer step;
    private Integer level;

    @ManyToOne
    private WorkoutEntity workout;

    public WorkoutEventEntity(int step, int level, WorkoutEntity workout, PeriodType periodType, LocalDateTime eventDateTime, UserEntity user) {
        super(EventType.WORKOUT, periodType, eventDateTime, user);
        this.step = step;
        this.level = level;
        this.workout = workout;
    }

    public WorkoutEventEntity(WorkoutEvent event) {
        super(event);
        this.step = event.getStep();
        this.level = event.getLevel();
        this.workout = event.getWorkout();
    }
}