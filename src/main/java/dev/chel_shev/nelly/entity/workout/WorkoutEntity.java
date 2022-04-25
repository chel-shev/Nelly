package dev.chel_shev.nelly.entity.workout;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "workout")
public class WorkoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private byte[] image;
    private String fileId;
    private LocalTime basicTime = LocalTime.of(8, 0);

    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER)
    private List<WorkoutExerciseEntity> exercises;
}