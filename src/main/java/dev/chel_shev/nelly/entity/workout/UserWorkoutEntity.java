package dev.chel_shev.nelly.entity.workout;

import dev.chel_shev.nelly.entity.EventEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_workout")
public class UserWorkoutEntity extends EventEntity {

    private int number;
    private int level;
    private LocalDateTime time;

    @OneToMany
    private List<WorkoutEntity> workout;

    @ManyToOne
    private UserEntity user;
}
