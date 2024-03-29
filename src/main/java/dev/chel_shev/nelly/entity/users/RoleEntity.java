package dev.chel_shev.nelly.entity.users;

import dev.chel_shev.nelly.type.RoleType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

//    @OneToMany(mappedBy = "resource")
//    private List<RoleResourceEntity> resources;
}
