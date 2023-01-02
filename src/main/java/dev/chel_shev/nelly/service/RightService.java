package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.users.RoleEntity;
import dev.chel_shev.nelly.entity.users.RoleResourceEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.repository.RoleRepository;
import dev.chel_shev.nelly.repository.RoleResourceRepository;
import dev.chel_shev.nelly.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RightService {

    private final RoleResourceRepository roleResourceRepository;
    private final RoleRepository roleRepository;

    public boolean isAvailable(UserEntity user, String resource) {
        List<RoleResourceEntity> allByRole = roleResourceRepository.findAllByRole(user.getRole());
        return allByRole.stream().anyMatch(e -> e.getResource().getName().equals(resource));
    }

    public RoleEntity getRole(RoleType role) {
        return roleRepository.getByRoleType(role);
    }
}
