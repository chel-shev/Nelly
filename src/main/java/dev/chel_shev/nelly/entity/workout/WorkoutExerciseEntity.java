package dev.chel_shev.nelly.entity.workout;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "workout_exercises")
@IdClass(value = WorkoutExerciseEntity.WorkoutExercisesEntityId.class)
public class WorkoutExerciseEntity {

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