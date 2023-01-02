package dev.chel_shev.nelly.security.client;

import dev.chel_shev.nelly.entity.users.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {

    @Getter
    private final UserEntity userEntity;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, UserEntity userEntity) {
        super(username, password, authorities);
        this.userEntity = userEntity;
    }

    public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, UserEntity userEntity) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userEntity = userEntity;
    }
}
