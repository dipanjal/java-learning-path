package com.example.restapi.service.auth;

import com.example.restapi.config.CurrentUser;
import com.example.restapi.entity.RoleEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.RecordNotFoundException;
import com.example.restapi.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.mapToUserDetails(
                userRepository.findByUserName(username)
                        .orElseThrow(() -> new RecordNotFoundException(
                                "User not found"
                        ))
        );
    }

    public User mapToUserDetails(final UserEntity entity) {
        return new CurrentUser(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                getGrantedAuthorities(entity.getRoles())
        );
    }
    private List<GrantedAuthority> getGrantedAuthorities(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
