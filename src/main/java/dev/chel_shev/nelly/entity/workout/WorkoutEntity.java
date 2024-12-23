package dev.chel_shev.nelly.entity.workout;

import dev.chel_shev.fast.entity.FastCommandEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "workout")
public class WorkoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private byte[] image;
    private String fileId;
    private Integer countExercise;
    private LocalTime basicTime = LocalTime.of(8, 0);
    private boolean progressable = false;
    @ManyToOne
    private FastCommandEntity command;
}