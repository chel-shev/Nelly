package dev.chel_shev.nelly.entity.workout;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "workout_exercise")
public class WorkoutExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WorkoutEntity workout;

    @ManyToOne
    private ExerciseEntity exercise;
}