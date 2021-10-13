package dev.chel_shev.nelly.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bday")
@AllArgsConstructor
@NoArgsConstructor
public class BdayEntity extends EventEntity {

    private String name;
    private LocalDateTime date;
}
