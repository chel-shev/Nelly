package dev.chel_shev.nelly.security.service;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.repository.user.UserRepository;
import dev.chel_shev.nelly.security.client.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByUserNameOrEmail(username, username);
        UserEntity userEntity = userOptional.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));
        return new UserDetails(userEntity.getUserName(), userEntity.getPassword(), authorities, userEntity);
    }
}
