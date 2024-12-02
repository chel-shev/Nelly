package dev.chel_shev.mind.entity;

import dev.chel_shev.mind.type.ViewType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "view")
public class MiViewEntity {

    @Id
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ViewType viewType;
}