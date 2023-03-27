package dev.chel_shev.nelly.entity.users;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "role_resource")
public class RoleResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoleEntity role;

    @ManyToOne
    private ResourceEntity resource;
}