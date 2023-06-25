package dev.chel_shev.fast.event.workout;

import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventId;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Setter
@Getter
@FastEventId(command = "/workout")
public class FastWorkoutEvent extends FastEvent {

    private Integer step;
    private Integer level;
    private WorkoutEntity workout;

    public FastEventEntity getEntity() {
        return new FastWorkoutEventEntity(this);
    }

    @Override
    public void init(FastEventEntity event, FastUserSubscriptionEntity user) {
        super.init(event, user);
        this.step = ((FastWorkoutEventEntity) event).getStep();
        this.level = ((FastWorkoutEventEntity) event).getLevel();
        this.workout = ((FastWorkoutEventEntity) event).getWorkout();
    }

    @Override
    public boolean isNotReadyForExecute() {
        return !step.equals(workout.getCountExercise());
    }

    public void incStep() {
        if (step < workout.getCountExercise() - 1) step++;
    }

    public void decStep() {
        if (step > 0) step--;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", step=" + step +
                ", level=" + level +
                ", workout=" + workout.getName() +
                '}';
    }
}
