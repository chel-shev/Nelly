package dev.chel_shev.nelly.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "workout")
public class WorkoutEntity extends EventEntity {

    private String name;

    @ManyToMany(mappedBy = "workoutList")
    private List<ExerciseEntity> exerciseList;
}