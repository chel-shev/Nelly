package dev.chel_shev.nelly.entity.workout;

import dev.chel_shev.nelly.type.ExerciseType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exercise")
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String comment;
    private String fileId;
    private byte[] image;
    private Integer reps;
    @Enumerated(EnumType.STRING)
    private ExerciseType type;
}