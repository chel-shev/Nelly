package dev.chel_shev.nelly.entity.users;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "resource")
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}