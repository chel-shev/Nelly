package dev.chel_shev.nelly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "bday")
@AllArgsConstructor
@NoArgsConstructor
public class BdayEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate date;

    @ManyToOne
    private UserEntity user;
}
