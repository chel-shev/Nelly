package dev.chel_shev.nelly.entity.workout;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "workout_exercises")
@IdClass(value = WorkoutExercisesEntity.WorkoutExercisesEntityId.class)
public class WorkoutExercisesEntity {

    @Id
    @ManyToOne
    private WorkoutEntity workout;

    @Id
    @ManyToOne
    private ExerciseEntity exercise;

    private int orderExercise;

    @Data
    public static class WorkoutExercisesEntityId implements Serializable {
        private long workout;
        private long exercise;
    }
}