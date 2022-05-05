package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.users.RoleEntity;
import dev.chel_shev.nelly.entity.users.RoleResourceEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResourceEntity, Long> {
    List<RoleResourceEntity> findAllByRole(RoleEntity role);
}
