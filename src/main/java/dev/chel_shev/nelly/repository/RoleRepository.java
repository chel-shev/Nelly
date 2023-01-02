package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.users.RoleEntity;
import dev.chel_shev.nelly.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity getByRoleType(RoleType type);
}
