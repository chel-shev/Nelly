package dev.chel_shev.nelly.bot.event.workout;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventId;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EventId(EventType.WORKOUT)
public class WorkoutEvent extends Event {

    private Integer step;
    private Integer level;
    private WorkoutEntity workout;

    public EventEntity getEntity() {
        return new WorkoutEventEntity(this);
    }

    @Override
    public void init(EventEntity entity, UserEntity user) {
        super.init(entity, user);
        this.step = ((WorkoutEventEntity) entity).getStep();
        this.level = ((WorkoutEventEntity) entity).getLevel();
        this.workout = ((WorkoutEventEntity) entity).getWorkout();
    }

    @Override
    public boolean isNotReadyForExecute() {
        return step != workout.getExercises().size();
    }

    public void incStep() {
        step++;
    }
}
